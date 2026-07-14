package com.CSC340.MealPrep_Match.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CSC340.MealPrep_Match.entity.Recipe;
import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.entity.Subscription;
import com.CSC340.MealPrep_Match.repository.CustomerRepository;
import com.CSC340.MealPrep_Match.repository.RecipeRepository;
import com.CSC340.MealPrep_Match.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final RecipeRepository recipeRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CustomerRepository customerRepository,
            RecipeRepository recipeRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getByCustomer(Long customerId) {
        return subscriptionRepository.findByCustomer_CustomerId(customerId);
    }

    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found: " + id));
    }

    public Subscription create(Subscription subscription) {
        if (subscription.getCustomer() == null || subscription.getCustomer().getCustomerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer.customerId is required");
        }
        if (subscription.getRecipe() == null || subscription.getRecipe().getRecipeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe.recipeId is required");
        }

        Customer customer = customerRepository.findById(subscription.getCustomer().getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found: " + subscription.getCustomer().getCustomerId()));
        Recipe recipe = recipeRepository.findById(subscription.getRecipe().getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recipe not found: " + subscription.getRecipe().getRecipeId()));

        return subscriptionRepository.findByCustomer_CustomerIdAndRecipe_RecipeId(customer.getCustomerId(),
                recipe.getRecipeId())
                .orElseGet(() -> {
                    subscription.setCustomer(customer);
                    subscription.setRecipe(recipe);
                    subscription.setSubscribedAt(Instant.now());
                    return subscriptionRepository.save(subscription);
                });
    }

    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found: " + id);
        }
        subscriptionRepository.deleteById(id);
    }
}
