package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UnderwriterController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/api/underwriter/claims")
    public ResponseEntity<List<Claim>> getAllClaimsForReview(@RequestParam Long underwriterId) {
        return new ResponseEntity<>(claimService.getAllClaimsForReview(underwriterId), HttpStatus.OK);
    }

    @PutMapping("/api/underwriter/claim/{id}/review")
    public ResponseEntity<Claim> reviewClaim(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(claimService.reviewClaim(id, status), HttpStatus.CREATED);
    }


    


    // 
    // public ResponseEntity<Claim> reviewClaim {
    //     return null;
            
    // }

    // 
    // public ResponseEntity<List<Claim>> getClaimsForReview {
    //     return null;
    //     // get all claims for review by underwriter id
    // }
}
