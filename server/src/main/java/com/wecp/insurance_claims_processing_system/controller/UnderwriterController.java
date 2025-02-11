package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class UnderwriterController {



    @PutMapping("/api/underwriter/claim/{id}/review")
    public ResponseEntity<Claim> reviewClaim(@PathVariable Long id, @RequestParam String status) {
       // review claim and update claim status
    }

    @GetMapping("/api/underwriter/claims")
    public ResponseEntity<List<Claim>> getClaimsForReview(@RequestParam Long underwriterId) {
        // get all claims for review by underwriter id
    }
}
