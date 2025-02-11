package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.entity.Underwriter;
import com.wecp.insurance_claims_processing_system.repository.UnderwriterRepository;
import com.wecp.insurance_claims_processing_system.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class AdjusterController {


    @PutMapping("/api/adjuster/claim/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable Long id, @RequestBody Claim claimDetails) {
        // update claim
    }

    @GetMapping("/api/adjuster/claims")
    public List<Claim> getAllClaims() {
        // get all claims
    }

    @GetMapping("/api/adjuster/underwriters")
    public List<Underwriter> getAllUnderwriters() {
        // get all underwriter
    }


    @PutMapping("/api/adjuster/claim/{claimId}/assign")
    public ResponseEntity<Claim> assignClaimToUnderwriter(
            @PathVariable Long claimId,
            @RequestParam Long underwriterId) {
        // assign claim to underwriter
    }


}
