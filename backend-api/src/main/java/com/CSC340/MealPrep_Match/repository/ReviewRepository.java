package com.CSC340.MealPrep_Match.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCustomer_CustomerId(Long customerId);

    List<Review> findByRecipe_RecipeId(Long recipeId);
}
