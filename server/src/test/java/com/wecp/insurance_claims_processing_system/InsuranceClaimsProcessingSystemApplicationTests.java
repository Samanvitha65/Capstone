package com.wecp.insurance_claims_processing_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecp.insurance_claims_processing_system.dto.LoginRequest;
import com.wecp.insurance_claims_processing_system.entity.*;
import com.wecp.insurance_claims_processing_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class InsuranceClaimsProcessingSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdjusterRepository adjusterRepository;

	@Autowired
	private PolicyholderRepository policyholderRepository;

	@Autowired
	private InvestigatorRepository investigatorRepository;

	@Autowired
	private UnderwriterRepository underwriterRepository;

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private InvestigationRepository investigationRepository;

	@BeforeEach
	public void setUp() {
		// Clear the database before each test
		userRepository.deleteAll();
		adjusterRepository.deleteAll();
		policyholderRepository.deleteAll();
		investigatorRepository.deleteAll();
		underwriterRepository.deleteAll();
		claimRepository.deleteAll();
		investigationRepository.deleteAll();
	}

	@Test
	public void testRegisterUser() throws Exception {
		// Create a User object for registration
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("testPassword");
		user.setEmail("test@example.com");
		user.setRole("ADJUSTER");

		// Perform a POST request to the /register endpoint using MockMvc
		mockMvc.perform(post("/api/user/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(user)))
				.andExpect(jsonPath("$.username").value(user.getUsername()))
				.andExpect(jsonPath("$.email").value(user.getEmail()))
				.andExpect(jsonPath("$.role").value(user.getRole()));

		// Assert business is created in the database
		User savedUser = userRepository.findAll().get(0);
		assertEquals(user.getUsername(), savedUser.getUsername());
		assertEquals(user.getEmail(), savedUser.getEmail());
		assertEquals(user.getRole(), savedUser.getRole());
	}

	@Test
	public void testLoginUser() throws Exception {
		// Create a user for registration
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("password");
		user.setRole("ADJUSTER");
		user.setEmail("testUser@gmail.com");
		// Register the user
		mockMvc.perform(post("/api/user/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)));

		// Login with the registered user
		LoginRequest loginRequest = new LoginRequest("testUser", "password");

		mockMvc.perform(post("/api/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(jsonPath("$.token").exists());
	}

	@Test
	public void testLoginWithWrongUsernameOrPassword() throws Exception {
		// Create a login request with a wrong username
		LoginRequest loginRequest = new LoginRequest("wronguser", "password");

		mockMvc.perform(post("/api/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(status().isUnauthorized()); // Expect a 401 Unauthorized response
	}

	@Test
	@WithMockUser(username = "testPolicyHolder", authorities = {"POLICYHOLDER"})
	public void testPolicyHolderShouldSubmitClaim() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("John Doe");
		policyholder.setPassword("password");
		policyholder.setEmail("johndoe@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholder = policyholderRepository.save(policyholder);

		// Create a claim
		Claim claim = new Claim();
		claim.setDescription("Test claim");
		claim.setStatus("SUBMITTED");

		// Perform the submit claim request
		mockMvc.perform(post("/api/policyholder/claim")
						.param("policyholderId", policyholder.getId().toString())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(claim)))
				.andExpect(jsonPath("$.description").value("Test claim"))
				.andExpect(jsonPath("$.status").value("SUBMITTED"))
				.andExpect(jsonPath("$.policyholder.id").value(policyholder.getId()));

		// Verify the claim in the database
		List<Claim> claims = claimRepository.findAll();
		assertEquals(1, claims.size());
		assertEquals("Test claim", claims.get(0).getDescription());
		assertEquals("SUBMITTED", claims.get(0).getStatus());
		assertEquals(policyholder.getId(), claims.get(0).getPolicyholder().getId());
	}

	@Test
	@WithMockUser(username = "testPolicyHolder", authorities = {"POLICYHOLDER"})
	public void testPolicyHolderShouldGetClaims() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("Jane Smith");
		policyholder.setPassword("password");
		policyholder.setEmail("janesmith@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholder = policyholderRepository.save(policyholder);

		// Create and save claims
		Claim claim1 = new Claim();
		claim1.setDescription("Claim 1");
		claim1.setStatus("SUBMITTED");
		claim1.setPolicyholder(policyholder);
		claimRepository.save(claim1);

		Claim claim2 = new Claim();
		claim2.setDescription("Claim 2");
		claim2.setStatus("SUBMITTED");
		claim2.setPolicyholder(policyholder);
		claimRepository.save(claim2);

		// Perform the get claims request
		mockMvc.perform(get("/api/policyholder/claims")
						.param("policyholderId", policyholder.getId().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].description").value("Claim 1"))
				.andExpect(jsonPath("$[1].description").value("Claim 2"));

		// Verify the claims in the database
		List<Claim> claims = claimRepository.findAll();
		assertEquals(2, claims.size());
		assertEquals("Claim 1", claims.get(0).getDescription());
		assertEquals("Claim 2", claims.get(1).getDescription());
		assertEquals(policyholder.getId(), claims.get(0).getPolicyholder().getId());
		assertEquals(policyholder.getId(), claims.get(1).getPolicyholder().getId());
	}

	@Test
	@WithMockUser(username = "testAdjuster", authorities = {"ADJUSTER"})
	public void testAdjusterShouldUpdateClaim() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("John Doe");
		policyholder.setPassword("password");
		policyholder.setEmail("johndoe@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholderRepository.save(policyholder);

		// Create and save a claim
		Claim claim = new Claim();
		claim.setDescription("Initial Description");
		claim.setStatus("SUBMITTED");
		claim.setPolicyholder(policyholder);
		claimRepository.save(claim);

		// Update the claim details
		Claim updatedDetails = new Claim();
		updatedDetails.setDescription("Updated Description");
		updatedDetails.setStatus("APPROVED");

		// Perform the update claim request
		mockMvc.perform(put("/api/adjuster/claim/" + claim.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedDetails)))
				.andExpect(jsonPath("$.description").value("Updated Description"))
				.andExpect(jsonPath("$.status").value("APPROVED"));

		// Verify the updated claim in the database
		Claim updatedClaim = claimRepository.findById(claim.getId()).orElse(null);
		assertEquals("Updated Description", updatedClaim.getDescription());
		assertEquals("APPROVED", updatedClaim.getStatus());
	}

	@Test
	@WithMockUser(username = "testAdjuster", authorities = {"ADJUSTER"})
	public void testAdjusterShouldGetAllClaims() throws Exception {
		// Create and save policyholders and claims
		Policyholder policyholder1 = new Policyholder();
		policyholder1.setUsername("John Doe");
		policyholder1.setPassword("password");
		policyholder1.setRole("POLICYHOLDER");
		policyholder1.setEmail("johndoe@example.com");
		policyholderRepository.save(policyholder1);

		Policyholder policyholder2 = new Policyholder();
		policyholder2.setUsername("Jane Smith");
		policyholder2.setPassword("password");
		policyholder2.setEmail("janesmith@example.com");
		policyholder2.setRole("POLICYHOLDER");
		policyholderRepository.save(policyholder2);

		Claim claim1 = new Claim();
		claim1.setDescription("Claim 1");
		claim1.setStatus("SUBMITTED");
		claim1.setPolicyholder(policyholder1);
		claimRepository.save(claim1);

		Claim claim2 = new Claim();
		claim2.setDescription("Claim 2");
		claim2.setStatus("SUBMITTED");
		claim2.setPolicyholder(policyholder2);
		claimRepository.save(claim2);

		// Perform the get all claims request
		mockMvc.perform(get("/api/adjuster/claims")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].description").value(("Claim 1")))
				.andExpect(jsonPath("$[1].description").value(("Claim 2")))
				.andExpect(jsonPath("$[0].policyholder.username").value(policyholder1.getUsername()))
				.andExpect(jsonPath("$[1].policyholder.username").value(policyholder2.getUsername()));

		// Verify the claims in the database
		List<Claim> claims = claimRepository.findAll();
		assertEquals(2, claims.size());
		assertEquals("Claim 1", claims.get(0).getDescription());
		assertEquals("Claim 2", claims.get(1).getDescription());
	}

	@Test
	@WithMockUser(username = "testAdjuster", authorities = {"ADJUSTER"})
	public void testAdjusterShouldAssignClaimToUnderwriter() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("John Doe");
		policyholder.setPassword("password");
		policyholder.setEmail("johndoe@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholderRepository.save(policyholder);

		// Create and save a claim
		Claim claim = new Claim();
		claim.setDescription("Test claim");
		claim.setStatus("SUBMITTED");
		claim.setPolicyholder(policyholder);
		claimRepository.save(claim);

		// Create and save an underwriter
		Underwriter underwriter = new Underwriter();
		underwriter.setUsername("Alice Johnson");
		underwriter.setPassword("password");
		underwriter.setEmail("alicejohnson@example.com");
		underwriter.setRole("UNDERWRITER");
		underwriter = underwriterRepository.save(underwriter);

		// Perform the assign claim to underwriter request
		mockMvc.perform(put("/api/adjuster/claim/" + claim.getId() + "/assign")
						.param("underwriterId", underwriter.getId().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Test claim"))
				.andExpect(jsonPath("$.underwriter.id").value((underwriter.getId().intValue())));

		// Verify the assignment in the database
		Claim assignedClaim = claimRepository.findById(claim.getId()).orElse(null);

		assertEquals(underwriter.getId(), assignedClaim.getUnderwriter().getId());
	}

	@Test
	@WithMockUser(username = "testInvestigator", authorities = {"INVESTIGATOR"})
	public void testInvestigatorShouldCreateInvestigation() throws Exception {
		// Create and save a claim
		Claim claim = new Claim();
		claim.setDescription("Test claim");
		claim.setStatus("SUBMITTED");
		claimRepository.save(claim);

		// Create a new investigation
		Investigation investigation = new Investigation();
		investigation.setClaim(claim);
		investigation.setReport("Investigation details");
		investigation.setStatus("IN_PROGRESS");

		// Perform the create investigation request
		mockMvc.perform(post("/api/investigator/investigation")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(investigation)))
				.andExpect(jsonPath("$.report").value("Investigation details"))
				.andExpect(jsonPath("$.claim.description").value("Test claim"));

		// Verify the investigation in the database
		Investigation createdInvestigation = investigationRepository.findAll().get(0);
		assertEquals("Investigation details", createdInvestigation.getReport());
		assertEquals(claim.getId(), createdInvestigation.getClaim().getId());
	}

	@Test
	@WithMockUser(username = "testInvestigator", authorities = {"INVESTIGATOR"})
	public void testInvestigatorShouldUpdateInvestigation() throws Exception {
		// Create and save a claim
		Claim claim = new Claim();
		claim.setDescription("Test claim");
		claim.setStatus("SUBMITTED");
		claimRepository.save(claim);

		// Create and save an investigation
		Investigation investigation = new Investigation();
		investigation.setClaim(claim);
		investigation.setReport("Initial details");
		investigation = investigationRepository.save(investigation);

		// Update the investigation details
		Investigation updatedDetails = new Investigation();
		updatedDetails.setReport("Updated details");

		// Perform the update investigation request
		mockMvc.perform(put("/api/investigator/investigation/" + investigation.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedDetails)))
				.andExpect(jsonPath("$.report").value("Updated details"));

		// Verify the updated investigation in the database
		Investigation updatedInvestigation = investigationRepository.findById(investigation.getId()).orElse(null);
		assertEquals("Updated details", updatedInvestigation.getReport());
	}

	@Test
	@WithMockUser(username = "testInvestigator", authorities = {"INVESTIGATOR"})
	public void testInvestigatorShouldGetAllInvestigations() throws Exception {
		// Create and save claims and investigations
		Claim claim1 = new Claim();
		claim1.setDescription("Claim 1");
		claim1.setStatus("SUBMITTED");
		claimRepository.save(claim1);

		Claim claim2 = new Claim();
		claim2.setDescription("Claim 2");
		claim2.setStatus("SUBMITTED");
		claimRepository.save(claim2);

		Investigation investigation1 = new Investigation();
		investigation1.setClaim(claim1);
		investigation1.setReport("Investigation 1");
		investigationRepository.save(investigation1);

		Investigation investigation2 = new Investigation();
		investigation2.setClaim(claim2);
		investigation2.setReport("Investigation 2");
		investigationRepository.save(investigation2);

		// Perform the get all investigations request
		mockMvc.perform(get("/api/investigator/investigations")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].report").value("Investigation 1"))
				.andExpect(jsonPath("$[1].report").value("Investigation 2"));

		// Verify the investigations in the database
		List<Investigation> investigations = investigationRepository.findAll();
		assertEquals(2, investigations.size());
		assertEquals("Investigation 1", investigations.get(0).getReport());
		assertEquals("Investigation 2", investigations.get(1).getReport());
	}

	@Test
	@WithMockUser(username = "testUnderwriter", authorities = {"UNDERWRITER"})
	public void testUnderwriterShouldReviewClaim() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("John Doe");
		policyholder.setPassword("password");
		policyholder.setEmail("johndoe@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholderRepository.save(policyholder);

		// Create and save a claim
		Claim claim = new Claim();
		claim.setDescription("Test claim");
		claim.setStatus("SUBMITTED");
		claim.setPolicyholder(policyholder);
		claim = claimRepository.save(claim);

		// Perform the review claim request
		mockMvc.perform(put("/api/underwriter/claim/" + claim.getId() + "/review")
						.param("status", "APPROVED")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("APPROVED"));

		// Verify the updated claim in the database
		Claim reviewedClaim = claimRepository.findById(claim.getId()).orElse(null);
		assertEquals("APPROVED", reviewedClaim.getStatus());
	}

	@Test
	@WithMockUser(username = "testUnderwriter", authorities = {"UNDERWRITER"})
	public void testUnderwriterShouldGetClaimsForReview() throws Exception {
		// Create and save a policyholder
		Policyholder policyholder = new Policyholder();
		policyholder.setUsername("John Doe");
		policyholder.setPassword("password");
		policyholder.setEmail("johndoe@example.com");
		policyholder.setRole("POLICYHOLDER");
		policyholderRepository.save(policyholder);

		// Create and save an underwriter
		Underwriter underwriter = new Underwriter();
		underwriter.setUsername("Alice Johnson");
		underwriter.setPassword("password");
		underwriter.setEmail("alicejohnson@example.com");
		underwriter.setRole("UNDERWRITER");
		underwriterRepository.save(underwriter);

		// Create and save claims
		Claim claim1 = new Claim();
		claim1.setDescription("Claim 1");
		claim1.setStatus("SUBMITTED");
		claim1.setPolicyholder(policyholder);
		claim1.setUnderwriter(underwriter);
		claimRepository.save(claim1);

		Claim claim2 = new Claim();
		claim2.setDescription("Claim 2");
		claim2.setStatus("SUBMITTED");
		claim2.setPolicyholder(policyholder);
		claim2.setUnderwriter(underwriter);
		claimRepository.save(claim2);

		// Perform the get claims for review request
		mockMvc.perform(get("/api/underwriter/claims")
						.param("underwriterId", underwriter.getId().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].description").value("Claim 1"))
				.andExpect(jsonPath("$[1].description").value("Claim 2"));

		// Verify the claims in the database
		List<Claim> claims = claimRepository.findAll();
		assertEquals(2, claims.size());
		assertEquals("Claim 1", claims.get(0).getDescription());
		assertEquals("Claim 2", claims.get(1).getDescription());
	}

	@Test
	@WithMockUser(authorities = {"ADJUSTER", "UNDERWRITER", "INVESTIGATOR"})
	public void testPolicyHolderApiShouldBeForbiddenForOtherUser() throws Exception {
		mockMvc.perform(post("/api/policyholder/claim")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());

		mockMvc.perform(get("/api/policyholder/claims")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"POLICYHOLDER", "UNDERWRITER", "INVESTIGATOR"})
	public void testAdjusterApiShouldBeForbiddenForOtherUser() throws Exception {
		mockMvc.perform(put("/api/adjuster/claim/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());

		mockMvc.perform(get("/api/adjuster/claims")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());

		mockMvc.perform(put("/api/adjuster/claim/1/assign")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"POLICYHOLDER", "ADJUSTER", "INVESTIGATOR"})
	public void testUnderwriterApiShouldBeForbiddenForOtherUser() throws Exception {
		mockMvc.perform(put("/api/underwriter/claim/1/review")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());

		mockMvc.perform(get("/api/underwriter/claims")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"POLICYHOLDER", "ADJUSTER", "UNDERWRITER"})
	public void testInvestigatorApiShouldBeForbiddenForOtherUser() throws Exception {
		mockMvc.perform(post("/api/investigator/investigation")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());

		mockMvc.perform(put("/api/investigator/investigation/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isForbidden());

		mockMvc.perform(get("/api/investigator/investigations")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
}
