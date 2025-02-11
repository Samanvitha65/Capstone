package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.entity.Claim;
import com.wecp.insurance_claims_processing_system.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PolicyholderController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/api/policyholder/claim")
    public ResponseEntity<Claim> submitClaim(@RequestParam Long policyholderId, @RequestBody Claim claim) {
        Claim submittedClaim = claimService.submitClaim(policyholderId, claim);
        return ResponseEntity.ok(submittedClaim);
    }

    @GetMapping("/api/policyholder/claims")
    public ResponseEntity<List<Claim>> getClaims(@RequestParam Long policyholderId) {
        List<Claim> claims = claimService.getClaimsByPolicyholder(policyholderId);
        return ResponseEntity.ok(claims);
    }
}
