package com.CSC340.MealPrep_Match.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CSC340.MealPrep_Match.entity.Recipe;
import com.CSC340.MealPrep_Match.entity.Review;
import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.repository.CustomerRepository;
import com.CSC340.MealPrep_Match.repository.RecipeRepository;
import com.CSC340.MealPrep_Match.repository.ReviewRepository;
import com.CSC340.MealPrep_Match.repository.SubscriptionRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RecipeRepository recipeRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository,
            RecipeRepository recipeRepository, SubscriptionRepository subscriptionRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.recipeRepository = recipeRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getByRecipe(Long recipeId) {
        return reviewRepository.findByRecipe_RecipeId(recipeId);
    }

    public List<Review> getByCustomer(Long customerId) {
        return reviewRepository.findByCustomer_CustomerId(customerId);
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found: " + id));
    }

    public Review create(Review review) {
        if (review.getCustomer() == null || review.getCustomer().getCustomerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer.customerId is required");
        }
        if (review.getRecipe() == null || review.getRecipe().getRecipeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe.recipeId is required");
        }

        Customer customer = customerRepository.findById(review.getCustomer().getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found: " + review.getCustomer().getCustomerId()));
        Recipe recipe = recipeRepository.findById(review.getRecipe().getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recipe not found: " + review.getRecipe().getRecipeId()));

        if (!subscriptionRepository.existsByCustomer_CustomerIdAndRecipe_RecipeId(customer.getCustomerId(),
                recipe.getRecipeId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Customer must be subscribed to this recipe before reviewing it");
        }

        review.setCustomer(customer);
        review.setRecipe(recipe);
        review.setCreatedAt(Instant.now());
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review updates) {
        Review review = getById(id);
        if (updates.getRating() != null) {
            review.setRating(updates.getRating());
        }
        if (updates.getComment() != null) {
            review.setComment(updates.getComment());
        }
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
