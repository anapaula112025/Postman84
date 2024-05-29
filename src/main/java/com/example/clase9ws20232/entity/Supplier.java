package com.example.clase9ws20232.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID", nullable = false)
    private Integer id;

    @Column(name = "CompanyName", nullable = false, length = 40)
    private String companyName;

    @Column(name = "ContactName", length = 30)
    private String contactName;

    @Column(name = "ContactTitle", length = 30)
    private String contactTitle;

    @Column(name = "Address", length = 60)
    private String address;

    @Column(name = "City", length = 15)
    private String city;

    @Column(name = "Region", length = 15)
    private String region;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Country", length = 15)
    private String country;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Lob
    @Column(name = "HomePage")
    private String homePage;


}