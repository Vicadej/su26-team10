package com.CSC340.MealPrep_Match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.model.Save;

public interface SaveRepository extends JpaRepository<Save, Long> {

    List<Save> findByCustomer_CustomerId(Long customerId);

    Optional<Save> findByCustomer_CustomerIdAndRecipe_RecipeId(Long customerId, Long recipeId);
}
