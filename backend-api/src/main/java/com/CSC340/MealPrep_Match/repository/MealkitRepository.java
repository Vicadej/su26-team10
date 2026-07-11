package com.CSC340.MealPrep_Match.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.entity.Mealkit;

public interface MealkitRepository extends JpaRepository<Mealkit, Long>{
    List<Mealkit> findByProviderId(Long providerId);

    List<Mealkit> findByCategoryContainingIgnoreCase(String category);

    List<Mealkit> findByTitleContainingIgnoreCaseOrDesriptionContainingIgnoreCase(String titleQuery, String descriptionQuery);
    
}
