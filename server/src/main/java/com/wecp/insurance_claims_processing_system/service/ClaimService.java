package com.wecp.insurance_claims_processing_system.service;


import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.entity.Policyholder;
import com.wecp.insurance_claims_processing_system.entity.Underwriter;
import com.wecp.insurance_claims_processing_system.repository.AdjusterRepository;
import com.wecp.insurance_claims_processing_system.repository.ClaimRepository;
import com.wecp.insurance_claims_processing_system.repository.PolicyholderRepository;
import com.wecp.insurance_claims_processing_system.repository.UnderwriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

@Autowired
private UnderwriterRepository underwriterRepository;

@Autowired
private ClaimRepository claimRepository;

@Autowired
private PolicyholderRepository policyholderRepository;

@Autowired
private AdjusterRepository adjusterRepository;



public Claim submitClaim(Long policyholderId, Claim claim){
    Policyholder ph = policyholderRepository.findById(policyholderId).get();
    claim.setPolicyholder(ph);
    return claimRepository.save(claim);
}



public List<Claim> getClaimsByPolicyholder(Long policyholderId){
    return claimRepository.findAll();
}

//Till here policyholdercontroller

public Claim updateClaim(Long id, Claim claimDetails){
    Claim c = claimRepository.findById(id).get();
    claimDetails.setId(c.getId());
    return claimRepository.save(claimDetails);
}

public List<Claim> getAllClaims(){
    return claimRepository.findAll();
}

public List<Underwriter> getAllUnderwriters(){
    return underwriterRepository.findAll();
}
 

//till here adjustercontroller


public List<Claim> getAllClaimsForReview(Long underwriterId){

    return claimRepository.findAll();

}

public Claim reviewClaim(Long id, String status){

    Claim c = claimRepository.findById(id).get();
    c.setStatus(status);
    return claimRepository.save(c);
}

//till here underwriter




 public Claim assignClaimToUnderwriter(Long claimId, Long underwriterId) {
        Optional<Claim> claimOptional = claimRepository.findById(claimId);
        Optional<Underwriter> underwriterOptional = underwriterRepository.findById(underwriterId);

        if (claimOptional.isPresent() && underwriterOptional.isPresent()) {
            Claim claim = claimOptional.get();
            Underwriter underwriter = underwriterOptional.get();
            claim.setUnderwriter(underwriter);
            return claimRepository.save(claim);
        } else {
            throw new IllegalArgumentException("Claim or Underwriter not found");
        }
 }

}
