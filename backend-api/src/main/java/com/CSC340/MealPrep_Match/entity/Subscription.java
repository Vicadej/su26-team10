package com.CSC340.MealPrep_Match.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "mealkit_id")
    private Mealkit mealkit;

    @ManyToOne
    @JoinColumn(name = "mealplan_id")
    private Mealplan mealplan;

    private Instant subscribedAt;

    public Subscription(Customer customer, Mealkit mealkit, Mealplan mealplan, Instant subscribedAt) {
        this.customer = customer;
        this.mealkit = mealkit;
        this.mealplan = mealplan;
        this.subscribedAt = subscribedAt;
    }
}
