package com.wecp.insurance_claims_processing_system.entity;

import javax.persistence.*;

@Entity
public class Investigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String report;
    private String status;

    @OneToOne(mappedBy = "investigation")
    private Claim claim;

    public Investigation() {
    }

    public Investigation(Long id, String report, String status, Claim claim) {
        this.id = id;
        this.report = report;
        this.status = status;
        this.claim = claim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    
    // implement investigation entity

    
}

