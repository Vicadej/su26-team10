package com.CSC340.MealPrep_Match.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mealplan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mealplan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({ "mealplan" })
    @JoinColumn(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    /* 
    @OneToMany(mappedBy = "mealplan")
    @JsonIgnore
    private List<Recipe> recipe;
    */

    public Mealplan(Provider provider, String title, String duration, String description, String category) {
        this.provider = provider;
        this.title = title;
        this.duration = duration;
        this.category = category;
    }
}
