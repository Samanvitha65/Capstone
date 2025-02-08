package com.wecp.insurance_claims_processing_system.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Policyholder extends User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @OneToMany(mappedBy = "policyholder")
    private Claim claim;
    // Implement entity and extend user
}