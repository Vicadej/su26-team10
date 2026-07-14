package com.CSC340.MealPrep_Match.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.CSC340.MealPrep_Match.repository.ProviderRepository;
import com.CSC340.MealPrep_Match.entity.Provider;

@Service
public class ProviderService {
    
    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> findById(long id) {
        return providerRepository.findById(id);
    }

    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public Provider updateProvider(Long id, Provider updatedProvider) {
        Optional<Provider> existingProvider = providerRepository.findById(id);
        if (existingProvider.isPresent()) {
            Provider provider = existingProvider.get();
            provider.setName(updatedProvider.getName());
            provider.setEmail(updatedProvider.getEmail());
            provider.setPassword(updatedProvider.getPassword());
            provider.setBio(updatedProvider.getBio());
            return providerRepository.save(provider);
        } else {
            throw new RuntimeException("Provider not found with id: " + id);
        }
    }

    public Provider updateProviderInfo(Long id, Provider updatedProvider) {
        Optional<Provider> existingProvider = providerRepository.findById(id);
        if (existingProvider.isPresent()) {
            Provider provider = existingProvider.get();
            if (updatedProvider.getName() != null) {
                provider.setName(updatedProvider.getName());
            }
            if (updatedProvider.getEmail() != null) {
                provider.setEmail(updatedProvider.getEmail());
            }
            if (updatedProvider.getPassword() != null) {
                provider.setPassword(updatedProvider.getPassword());
            }
            return providerRepository.save(provider);
        } else {
            throw new RuntimeException("Provider not found with id: " + id);
        }
    }

    public Provider updateProviderProfessionalInfo(Long id, Provider updatedProvider) {
        Optional<Provider> existingProvider = providerRepository.findById(id);
        if (existingProvider.isPresent()){
            Provider provider = existingProvider.get();
            if (updatedProvider.getBio() != null) {
                provider.setBio(updatedProvider.getBio());
            }
            if(updatedProvider.getSpecialties() != null) {
                provider.setSpecialties(updatedProvider.getSpecialties());
            }
            if(updatedProvider.getCertified() !=null) {
                provider.setCertified(updatedProvider.getCertified());
            }
            return providerRepository.save(provider);
        } else {
            throw new RuntimeException("Provider not found with id: " + id);
        }
    }

    public void deleteProvider(long id) {
        providerRepository.deleteById(id);
    }

    public Provider findByEmail(String email) {
        return providerRepository.findByEmail(email);
    }

    public List<Provider> findBySpecialty(String specialty) {
        return providerRepository.findBySpecialtiesContainingIgnoreCase(specialty);
    }


}
