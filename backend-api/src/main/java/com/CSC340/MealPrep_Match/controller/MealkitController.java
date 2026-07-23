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

import com.CSC340.MealPrep_Match.entity.Mealkit;
import com.CSC340.MealPrep_Match.service.MealkitService;

@RestController
@RequestMapping("/api/mealkits")
public class MealkitController {
    private MealkitService mealkitService;

    public MealkitController(MealkitService mealkit) {
        this.mealkitService = mealkit;
    }

    @GetMapping
    public ResponseEntity<List<Mealkit>> getAllMealkits() {
        List<Mealkit> mealkits = mealkitService.getAllMealkits();
        return ResponseEntity.ok(mealkits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mealkit> getMealkitById(@PathVariable Long id) {
        Mealkit mealkit = mealkitService.getMealkitById(id);
        if (mealkit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealkit);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Mealkit>> getMealkitByProviderId(@PathVariable Long providerId) {
        List<Mealkit> mealkits = mealkitService.getMealkitByProviderId(providerId);
        return ResponseEntity.ok(mealkits); 
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Mealkit>> getMealkitsByCategory(@PathVariable String category) {
        List<Mealkit> mealkits = mealkitService.getMealkitsByCategory(category);
        return ResponseEntity.ok(mealkits);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Mealkit>> searchMealkitsByTitle(@RequestParam String query) {
        List<Mealkit> mealkits = mealkitService.searchMealkitsByTitle(query);
        return ResponseEntity.ok(mealkits);
    }

    @PostMapping
    public ResponseEntity<Mealkit> createMealkit(@RequestBody Mealkit mealkit) {
        Mealkit createdMealkit = mealkitService.createMealkit(mealkit);
        return ResponseEntity.ok(createdMealkit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mealkit> updateMealkit(@PathVariable Long id, @RequestBody Mealkit mealkit) {
        Mealkit updatedMealkit = mealkitService.updateMealkit(id, mealkit);
        if (updatedMealkit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMealkit);
    }
}
