package com.CSC340.MealPrep_Match.model;

public record ReviewFormCard(
        RecipeCard recipe,
        Integer existingRating,
        String existingComment) {
}
