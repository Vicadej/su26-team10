package com.CSC340.MealPrep_Match.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CSC340.MealPrep_Match.entity.Provider;


@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>{
    Provider findByEmail(String email);

    List<Provider> findBySpecialtiesContainingIgnoreCase(String specialty);

}