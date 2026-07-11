package com.CSC340.MealPrep_Match.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.CSC340.MealPrep_Match.model.Save;
import com.CSC340.MealPrep_Match.service.SaveService;

@RestController
@RequestMapping("/api/saves")
public class SaveController {

    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @GetMapping
    public List<Save> getAll(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return saveService.getByCustomer(customerId);
        }
        return saveService.getAll();
    }

    @GetMapping("/{id}")
    public Save getById(@PathVariable Long id) {
        return saveService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Save create(@RequestBody Save save) {
        return saveService.create(save);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        saveService.delete(id);
    }
}
