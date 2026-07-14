package com.CSC340.MealPrep_Match.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private Double price;

    
    @ManyToMany
    @JoinTable(
            name = "mealplan_recipe",
            joinColumns = @JoinColumn(name = "mealplan_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    @JsonIgnore
    private List<Recipe> recipe;

    @ManyToMany(mappedBy = "mealplan")
    @JsonIgnore
    private List<Mealkit> mealkit;


    public Mealplan(Provider provider, String title, String duration, String description, String category, Double price) {
        this.provider = provider;
        this.title = title;
        this.duration = duration;
        this.category = category;
        this.price = price;
    }
}
