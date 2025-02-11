package com.wecp.insurance_claims_processing_system.controller;

import com.wecp.insurance_claims_processing_system.entity.Investigation;
import com.wecp.insurance_claims_processing_system.service.InvestigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
// public class InvestigatorController {


//     @Autowired
//     private InvestigationService investigationService;


//     @PostMapping("/api/investigator/investigation")
//     public ResponseEntity<Investigation> createInvestigation(@RequestBody Investigation investigation) {
//         return null;
//         // create investigation
//     }

//     @PutMapping("/api/investigator/investigation/{id}")
//     public ResponseEntity<Investigation> updateInvestigation(@PathVariable Long id, @RequestBody Investigation investigationDetails) {
//         return null;
//         // update investigation
//     }

//     @GetMapping("/api/investigator/investigations")
//     public List<Investigation> getAllInvestigations() {
//         return null;
//         // get all investigations
//     }
// }


//  import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

@RestController
@RequestMapping("/api/investigator")
public class InvestigatorController {

    @Autowired
    private InvestigationService investigationService;

    @PostMapping("/investigation")
    public ResponseEntity<Investigation> createInvestigation(@RequestBody Investigation investigation) {
        Investigation savedInvestigation = investigationService.createInvestigation(investigation);
        return ResponseEntity.ok(savedInvestigation);
    }

    @PutMapping("/investigation/{id}")
    public ResponseEntity<Investigation> updateInvestigation(@PathVariable Long id, @RequestBody Investigation investigationDetails) {
        Investigation updatedInvestigation = investigationService.updateInvestigation(id, investigationDetails);
        return ResponseEntity.ok(updatedInvestigation);
    }

    @GetMapping("/investigations")
    public ResponseEntity<List<Investigation>> getAllInvestigations() {
        List<Investigation> investigations = investigationService.findAllInvestigations();
        return ResponseEntity.ok(investigations);
    }
}
