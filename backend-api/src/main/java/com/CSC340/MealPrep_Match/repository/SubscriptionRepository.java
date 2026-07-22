package com.CSC340.MealPrep_Match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CSC340.MealPrep_Match.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByCustomer_CustomerId(Long customerId);

    List<Subscription> findByMealkit_Provider_Id(Long providerId);

    List<Subscription> findByMealplan_Provider_Id(Long providerId);

    boolean existsByCustomer_CustomerIdAndMealkit_Id(Long customerId, Long mealkitId);

    boolean existsByCustomer_CustomerIdAndMealplan_Id(Long customerId, Long mealplanId);

    Optional<Subscription> findByCustomer_CustomerIdAndMealkit_Id(Long customerId, Long mealkitId);

    Optional<Subscription> findByCustomer_CustomerIdAndMealplan_Id(Long customerId, Long mealplanId);
}
