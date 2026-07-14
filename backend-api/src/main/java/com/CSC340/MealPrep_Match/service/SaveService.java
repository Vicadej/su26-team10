package com.CSC340.MealPrep_Match.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CSC340.MealPrep_Match.entity.Recipe;
import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.entity.Save;
import com.CSC340.MealPrep_Match.repository.CustomerRepository;
import com.CSC340.MealPrep_Match.repository.RecipeRepository;
import com.CSC340.MealPrep_Match.repository.SaveRepository;

@Service
public class SaveService {

    private final SaveRepository saveRepository;
    private final CustomerRepository customerRepository;
    private final RecipeRepository recipeRepository;

    public SaveService(SaveRepository saveRepository, CustomerRepository customerRepository,
            RecipeRepository recipeRepository) {
        this.saveRepository = saveRepository;
        this.customerRepository = customerRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Save> getAll() {
        return saveRepository.findAll();
    }

    public List<Save> getByCustomer(Long customerId) {
        return saveRepository.findByCustomer_CustomerId(customerId);
    }

    public Save getById(Long id) {
        return saveRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Save not found: " + id));
    }

    public Save create(Save save) {
        if (save.getCustomer() == null || save.getCustomer().getCustomerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer.customerId is required");
        }
        if (save.getRecipe() == null || save.getRecipe().getRecipeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe.recipeId is required");
        }

        Customer customer = customerRepository.findById(save.getCustomer().getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found: " + save.getCustomer().getCustomerId()));
        Recipe recipe = recipeRepository.findById(save.getRecipe().getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recipe not found: " + save.getRecipe().getRecipeId()));

        return saveRepository.findByCustomer_CustomerIdAndRecipe_RecipeId(customer.getCustomerId(),
                recipe.getRecipeId())
                .orElseGet(() -> {
                    save.setCustomer(customer);
                    save.setRecipe(recipe);
                    save.setSavedAt(Instant.now());
                    return saveRepository.save(save);
                });
    }

    public void delete(Long id) {
        if (!saveRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Save not found: " + id);
        }
        saveRepository.deleteById(id);
    }
}
