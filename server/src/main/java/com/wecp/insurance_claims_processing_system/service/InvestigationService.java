package com.wecp.insurance_claims_processing_system.service;

import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.entity.Investigation;
import com.wecp.insurance_claims_processing_system.entity.Investigator;
import com.wecp.insurance_claims_processing_system.repository.ClaimRepository;
import com.wecp.insurance_claims_processing_system.repository.InvestigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

// @Service
// public class InvestigationService{
//     // implement investigation service here
 
//     @Autowired
//     private InvestigationRepository investigationRepository;
 
//     public Investigation createInvestigation(Investigation investigation) {
//          return investigationRepository.save(investigation);
//     }
 
 
//     public Investigation updateInvestigation(Long id,Investigation investigationDetails) {
       
//         // Investigation i = investigationRepository.findById(id);
//         investigationDetails.setId(id);
//         return investigationRepository.save(investigationDetails);
//     }

//     public Investigator getAllInvestigations(){
//         return investigationRepository.findAll();
//     }
// }


//  import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

@Service
public class InvestigationService {

    @Autowired
    private InvestigationRepository investigationRepository;

    @Autowired
    private ClaimRepository claimRepository;

    public Investigation createInvestigation(Investigation investigation) {
        // Ensure the claim is associated with the investigation
        if (investigation.getClaim() != null) {
            Optional<Claim> claimOptional = claimRepository.findById(investigation.getClaim().getId());
            if (claimOptional.isPresent()) {
                investigation.setClaim(claimOptional.get());
            }
        }
        return investigationRepository.save(investigation);
    }

    public List<Investigation> findAllInvestigations() {
        return investigationRepository.findAll();
    }

    public Optional<Investigation> findInvestigationById(Long id) {
        return investigationRepository.findById(id);
    }

    public Investigation updateInvestigation(Long id, Investigation investigationDetails) {
        return investigationRepository.findById(id).map(investigation -> {
            investigation.setReport(investigationDetails.getReport());
            investigation.setStatus(investigationDetails.getStatus());
            return investigationRepository.save(investigation);
        }).orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
    }
}
