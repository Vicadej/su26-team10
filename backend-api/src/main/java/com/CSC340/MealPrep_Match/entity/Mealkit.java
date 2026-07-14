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
@Table(name = "mealkit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mealkit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"mealkit"})
    @JoinColumn(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String deliveryFrequency;

    @Column(nullable = false)
    private String description;

    private String category;

    private Double price;

    @ManyToMany
    @JoinTable(
            name = "mealkit_mealplan",
            joinColumns = @JoinColumn(name = "mealkit_id"),
            inverseJoinColumns = @JoinColumn(name = "mealplan_id"))
    @JsonIgnore
    private List<Mealplan> mealplan;

    public Mealkit(Provider provider, String title, String deliveryFrequency, String description, String category) {
        this.provider = provider;
        this.title = title;
        this.deliveryFrequency = deliveryFrequency;
        this.description = description;
        this.category = category;
    }
    
}
