package com.CSC340.MealPrep_Match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.entity.Save;

public interface SaveRepository extends JpaRepository<Save, Long> {

    List<Save> findByCustomer_CustomerId(Long customerId);

    Optional<Save> findByCustomer_CustomerIdAndRecipe_RecipeId(Long customerId, Long recipeId);

    boolean existsByCustomer_CustomerIdAndRecipe_RecipeId(Long customerId, Long recipeId);

    List<Save> findByRecipe_Provider_Id(Long providerId);
}
