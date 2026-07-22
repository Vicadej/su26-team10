package com.CSC340.MealPrep_Match.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CSC340.MealPrep_Match.entity.Mealkit;
import com.CSC340.MealPrep_Match.entity.Mealplan;
import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.entity.Subscription;
import com.CSC340.MealPrep_Match.repository.CustomerRepository;
import com.CSC340.MealPrep_Match.repository.MealkitRepository;
import com.CSC340.MealPrep_Match.repository.MealplanRepository;
import com.CSC340.MealPrep_Match.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final MealkitRepository mealkitRepository;
    private final MealplanRepository mealplanRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CustomerRepository customerRepository,
            MealkitRepository mealkitRepository, MealplanRepository mealplanRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.mealkitRepository = mealkitRepository;
        this.mealplanRepository = mealplanRepository;
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

        boolean hasKit = subscription.getMealkit() != null && subscription.getMealkit().getId() != null;
        boolean hasPlan = subscription.getMealplan() != null && subscription.getMealplan().getId() != null;
        if (hasKit == hasPlan) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Provide exactly one of mealkit.id or mealplan.id");
        }

        Customer customer = customerRepository.findById(subscription.getCustomer().getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found: " + subscription.getCustomer().getCustomerId()));

        if (hasKit) {
            Mealkit mealkit = mealkitRepository.findById(subscription.getMealkit().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Mealkit not found: " + subscription.getMealkit().getId()));

            return subscriptionRepository
                    .findByCustomer_CustomerIdAndMealkit_Id(customer.getCustomerId(), mealkit.getId())
                    .orElseGet(() -> {
                        subscription.setCustomer(customer);
                        subscription.setMealkit(mealkit);
                        subscription.setMealplan(null);
                        subscription.setSubscribedAt(Instant.now());
                        return subscriptionRepository.save(subscription);
                    });
        } else {
            Mealplan mealplan = mealplanRepository.findById(subscription.getMealplan().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Mealplan not found: " + subscription.getMealplan().getId()));

            return subscriptionRepository
                    .findByCustomer_CustomerIdAndMealplan_Id(customer.getCustomerId(), mealplan.getId())
                    .orElseGet(() -> {
                        subscription.setCustomer(customer);
                        subscription.setMealplan(mealplan);
                        subscription.setMealkit(null);
                        subscription.setSubscribedAt(Instant.now());
                        return subscriptionRepository.save(subscription);
                    });
        }
    }

    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found: " + id);
        }
        subscriptionRepository.deleteById(id);
    }
}
