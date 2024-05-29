package com.example.clase9ws20232.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@JsonIgnoreProperties(value = {"picture"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID", nullable = false)
    private Integer id;

    @Column(name = "CategoryName", nullable = false, length = 15)
    private String categoryName;

    @Lob
    @Column(name = "Description")
    private String description;

    @Column(name = "Picture")
    private byte[] picture;

}