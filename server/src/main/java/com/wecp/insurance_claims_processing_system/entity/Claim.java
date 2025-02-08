package com.wecp.insurance_claims_processing_system.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Date date;
    private String status;
   @ManyToOne()
   private Policyholder policyholder;
   @ManyToOne()
   private Adjuster adjuster;
   @ManyToOne()
   private Underwriter underwriter;
   @ManyToOne()
   private Investigation investigation;
   public Claim(){

   }
public Claim(Long id, String description, Date date, String status, Policyholder policyholder, Adjuster adjuster,
        Underwriter underwriter, Investigation investigation) {
    this.id = id;
    this.description = description;
    this.date = date;
    this.status = status;
    this.policyholder = policyholder;
    this.adjuster = adjuster;
    this.underwriter = underwriter;
    this.investigation = investigation;
}
public long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}
public Date getDate() {
    return date;
}
public void setDate(Date date) {
    this.date = date;
}
public String getStatus() {
    return status;
}
public void setStatus(String status) {
    this.status = status;
}
public Policyholder getPolicyholder() {
    return policyholder;
}
public void setPolicyholder(Policyholder policyholder) {
    this.policyholder = policyholder;
}
public Adjuster getAdjuster() {
    return adjuster;
}
public void setAdjuster(Adjuster adjuster) {
    this.adjuster = adjuster;
}
public Underwriter getUnderwriter() {
    return underwriter;
}
public void setUnderwriter(Underwriter underwriter) {
    this.underwriter = underwriter;
}
public Investigation getInvestigation() {
    return investigation;
}
public void setInvestigation(Investigation investigation) {
    this.investigation = investigation;
}
   


    // implement entity
}
