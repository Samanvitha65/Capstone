package com.wecp.insurance_claims_processing_system.controller;

import com.wecp.insurance_claims_processing_system.entity.Investigation;
import com.wecp.insurance_claims_processing_system.service.InvestigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class InvestigatorController {


    @PostMapping("/api/investigator/investigation")
    public ResponseEntity<Investigation> createInvestigation(@RequestBody Investigation investigation) {
        // create investigation
    }

    @PutMapping("/api/investigator/investigation/{id}")
    public ResponseEntity<Investigation> updateInvestigation(@PathVariable Long id, @RequestBody Investigation investigationDetails) {
        // update investigation
    }

    @GetMapping("/api/investigator/investigations")
    public List<Investigation> getAllInvestigations() {
        // get all investigations
    }
}
