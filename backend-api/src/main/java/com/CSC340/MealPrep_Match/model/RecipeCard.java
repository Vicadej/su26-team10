package com.CSC340.MealPrep_Match.model;

import java.util.List;

public record RecipeCard(
        Long id,
        String title,
        String providerName,
        List<String> tags,
        List<String> ingredients,
        double averageRating,
        int reviewCount,
        boolean subscribed) {
}
