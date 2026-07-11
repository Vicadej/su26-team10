package com.CSC340.MealPrep_Match.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CSC340.MealPrep_Match.entity.Mealplan;
import com.CSC340.MealPrep_Match.service.MealplanService;

public interface MealplanRepository extends JpaRepository<Mealplan, Long>{
    List<Mealplan> findByProviderId(Long providerId);
    
    List<Mealplan> findByCategoryContainingIgnoreCase(String category);

    List<Mealplan> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleQuery, String descriptionQuery);
}
