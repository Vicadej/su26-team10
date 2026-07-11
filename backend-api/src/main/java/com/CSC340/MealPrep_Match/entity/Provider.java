package com.CSC340.MealPrep_Match.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String specialties;

    private Boolean certified;

    public Provider(String name, String email, String password, String bio, String specialties, Boolean certified) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.specialties = specialties;
        this.certified = certified;
    }

    /* 
    @OneToMany(mappedBy = "Provider")
    @JsonIgnoreProperties({ "Provider" })
    private List<Recipe> recipe;

    @OneToMany(mappedBy = "Provider")
    @JsonIgnoreProperties({ "Provider" })
    private List<Mealplan> mealplan;

    @OneToMany(mappedBy = "Provider")
    @JsonIgnoreProperties({ "Provider" })
    private List<Mealkit> mealkit;

    @OneToMany(mappedBy = "Provider")
    @JsonIgnoreProperties({ "Provider" })
    private List<Review> review;
    */
}
