package com.wecp.insurance_claims_processing_system.repository;

import com.wecp.insurance_claims_processing_system.entity.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface InvestigationRepository extends JpaRepository<Investigation,Long> {
    // Additional query methods can be defined here if needed
}
