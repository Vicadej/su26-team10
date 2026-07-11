package com.CSC340.MealPrep_Match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByCustomer_CustomerId(Long customerId);

    Optional<Subscription> findByCustomer_CustomerIdAndRecipe_RecipeId(Long customerId, Long recipeId);

    boolean existsByCustomer_CustomerIdAndRecipe_RecipeId(Long customerId, Long recipeId);
}
