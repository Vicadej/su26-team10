package com.CSC340.MealPrep_Match.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.entity.Recipe;
import com.CSC340.MealPrep_Match.entity.Review;
import com.CSC340.MealPrep_Match.entity.Subscription;
import com.CSC340.MealPrep_Match.model.RecipeCard;
import com.CSC340.MealPrep_Match.model.ReviewFormCard;
import com.CSC340.MealPrep_Match.service.CustomerService;
import com.CSC340.MealPrep_Match.service.RecipeService;
import com.CSC340.MealPrep_Match.service.ReviewService;
import com.CSC340.MealPrep_Match.service.SubscriptionService;

@Controller
@RequestMapping("/customer")
public class CustomerViewController {

    private final CustomerService customerService;
    private final RecipeService recipeService;
    private final SubscriptionService subscriptionService;
    private final ReviewService reviewService;

    public CustomerViewController(CustomerService customerService, RecipeService recipeService,
            SubscriptionService subscriptionService, ReviewService reviewService) {
        this.customerService = customerService;
        this.recipeService = recipeService;
        this.subscriptionService = subscriptionService;
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String login() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email, @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.authenticate(email, password);
            return "redirect:/customer/" + customer.getCustomerId() + "/dashboard";
        } catch (ResponseStatusException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
            return "redirect:/customer/login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "customer/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String name, @RequestParam String email,
            @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            Customer toCreate = new Customer();
            toCreate.setName(name);
            toCreate.setEmail(email);
            toCreate.setPassword(password);
            Customer created = customerService.create(toCreate);
            return "redirect:/customer/" + created.getCustomerId() + "/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not register with that email.");
            return "redirect:/customer/register";
        }
    }

    @GetMapping("/{id}/dashboard")
    public String dashboard(@PathVariable Long id, Model model) {
        customerService.getById(id);
        Set<Long> subscribedRecipeIds = subscribedRecipeIds(id);
        List<RecipeCard> recipes = recipeService.getAll().stream()
                .map(recipe -> toCard(recipe, subscribedRecipeIds))
                .toList();
        model.addAttribute("customerId", id);
        model.addAttribute("recipes", recipes);
        return "customer/dashboard";
    }

    @PostMapping("/{id}/subscribe/{recipeId}")
    public String subscribe(@PathVariable Long id, @PathVariable Long recipeId) {
        Subscription subscription = new Subscription();
        Customer customer = new Customer();
        customer.setCustomerId(id);
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        subscription.setCustomer(customer);
        subscription.setRecipe(recipe);
        subscriptionService.create(subscription);
        return "redirect:/customer/" + id + "/dashboard";
    }

    @GetMapping("/{id}/profile")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/profile";
    }

    @PostMapping("/{id}/profile")
    public String profileSubmit(@PathVariable Long id, @RequestParam String name, @RequestParam String email,
            @RequestParam(required = false) String dietaryPreferences) {
        Customer updates = new Customer();
        updates.setName(name);
        updates.setEmail(email);
        if (dietaryPreferences != null && !dietaryPreferences.isBlank()) {
            updates.setDietaryPreferences(new ArrayList<>(Arrays.asList(dietaryPreferences.split("\\s*,\\s*"))));
        }
        customerService.update(id, updates);
        return "redirect:/customer/" + id + "/profile";
    }

    @GetMapping("/{id}/subscriptions")
    public String subscriptions(@PathVariable Long id, Model model) {
        Set<Long> subscribedRecipeIds = subscribedRecipeIds(id);
        List<RecipeCard> subscriptions = subscriptionService.getByCustomer(id).stream()
                .map(subscription -> toCard(subscription.getRecipe(), subscribedRecipeIds))
                .toList();
        model.addAttribute("customerId", id);
        model.addAttribute("subscriptions", subscriptions);
        return "customer/subscriptions";
    }

    @GetMapping("/{id}/reviews")
    public String reviews(@PathVariable Long id, Model model) {
        Set<Long> subscribedRecipeIds = subscribedRecipeIds(id);
        Map<Long, Review> reviewsByRecipeId = reviewService.getByCustomer(id).stream()
                .collect(Collectors.toMap(review -> review.getRecipe().getRecipeId(), review -> review));

        List<ReviewFormCard> reviewCards = subscriptionService.getByCustomer(id).stream()
                .map(subscription -> {
                    RecipeCard card = toCard(subscription.getRecipe(), subscribedRecipeIds);
                    Review existing = reviewsByRecipeId.get(subscription.getRecipe().getRecipeId());
                    return new ReviewFormCard(card,
                            existing != null ? existing.getRating() : null,
                            existing != null ? existing.getComment() : null);
                })
                .toList();

        model.addAttribute("customerId", id);
        model.addAttribute("reviewCards", reviewCards);
        return "customer/reviews";
    }

    @PostMapping("/{id}/reviews/{recipeId}")
    public String reviewSubmit(@PathVariable Long id, @PathVariable Long recipeId,
            @RequestParam Integer rating, @RequestParam(required = false) String comment) {
        Review existing = reviewService.getByCustomer(id).stream()
                .filter(review -> review.getRecipe().getRecipeId().equals(recipeId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            Review updates = new Review();
            updates.setRating(rating);
            updates.setComment(comment);
            reviewService.update(existing.getReviewId(), updates);
        } else {
            Review review = new Review();
            Customer customer = new Customer();
            customer.setCustomerId(id);
            Recipe recipe = new Recipe();
            recipe.setRecipeId(recipeId);
            review.setCustomer(customer);
            review.setRecipe(recipe);
            review.setRating(rating);
            review.setComment(comment);
            reviewService.create(review);
        }
        return "redirect:/customer/" + id + "/reviews";
    }

    private Set<Long> subscribedRecipeIds(Long customerId) {
        return subscriptionService.getByCustomer(customerId).stream()
                .map(subscription -> subscription.getRecipe().getRecipeId())
                .collect(Collectors.toSet());
    }

    private RecipeCard toCard(Recipe recipe, Set<Long> subscribedRecipeIds) {
        List<Review> reviews = reviewService.getByRecipe(recipe.getRecipeId());
        double averageRating = reviews.isEmpty()
                ? 0.0
                : reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        String providerName = recipe.getProvider() != null ? recipe.getProvider().getName() : "Unknown Provider";
        return new RecipeCard(
                recipe.getRecipeId(),
                recipe.getTitle(),
                providerName,
                recipe.getTags(),
                recipe.getIngredients(),
                averageRating,
                reviews.size(),
                subscribedRecipeIds.contains(recipe.getRecipeId()));
    }
}
