package com.CSC340.MealPrep_Match.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSC340.MealPrep_Match.entity.Mealplan;
import com.CSC340.MealPrep_Match.service.MealplanService;

@RestController
@RequestMapping("/api/mealplans")
public class MealplanController {
    private MealplanService mealplanService;

    public MealplanController(MealplanService mealplan) {
        this.mealplanService = mealplan;
    }

    @GetMapping
    public ResponseEntity<List<Mealplan>> getAllMealplans() {
        List<Mealplan> mealplans = mealplanService.getAllMealplans();
        return ResponseEntity.ok(mealplans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mealplan> getMealplanById(@PathVariable Long id) {
        Mealplan mealplan = mealplanService.getMealplanById(id);
        if (mealplan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealplan);
    }

    @GetMapping("/provider/providerId")
    public ResponseEntity<List<Mealplan>> getMealplansByProviderId(@PathVariable Long providerId) {
        List<Mealplan> mealplans = mealplanService.getMealplansByProviderId(providerId);
        return ResponseEntity.ok(mealplans);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Mealplan>> getMealplansByCategory(@PathVariable String category) {
        List<Mealplan> mealplans = mealplanService.getMealplansByCategory(category);
        return ResponseEntity.ok(mealplans);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Mealplan>> searchMealplansByTitle(@RequestParam String query) {
        List<Mealplan> mealplans = mealplanService.searchMealplansByTitle(query);
        return ResponseEntity.ok(mealplans);
    }

    @PostMapping
    public ResponseEntity<Mealplan> createMealplan(@RequestBody Mealplan mealplan) {
        Mealplan createdMealplan = mealplanService.createMealplan(mealplan);
        return ResponseEntity.ok(createdMealplan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mealplan> updateMealplan(@PathVariable Long id, @RequestBody Mealplan mealplan) {
        Mealplan updatedMealplan = mealplanService.updateMealplan(id, mealplan);
        if (updatedMealplan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMealplan);
    }
}
