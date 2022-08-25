package org.java.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "street")
    private String street1;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "contact_email")
    private String contactEmail;
}
