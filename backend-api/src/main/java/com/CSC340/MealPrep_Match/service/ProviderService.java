package com.CSC340.MealPrep_Match.service;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.CSC340.MealPrep_Match.repository.ProviderRepository;
import com.CSC340.MealPrep_Match.repository.SaveRepository;
import com.CSC340.MealPrep_Match.repository.SubscriptionRepository;

import jakarta.transaction.Transactional;

import com.CSC340.MealPrep_Match.dto.ProviderStats;
import com.CSC340.MealPrep_Match.dto.UploadSummary;
import com.CSC340.MealPrep_Match.entity.Provider;
import com.CSC340.MealPrep_Match.entity.Review;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SaveRepository saveRepository;

    public ProviderService(ProviderRepository providerRepository, SubscriptionRepository subscriptionRepository,
            SaveRepository saveRepository) {
        this.providerRepository = providerRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.saveRepository = saveRepository;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Provider findById(long id) {
        return providerRepository.findById(id).orElse(null);
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
            if (updatedProvider.getBio() != null) {
                provider.setBio(updatedProvider.getBio());
            }
            if (updatedProvider.getSpecialties() != null) {
                provider.setSpecialties(updatedProvider.getSpecialties());
            }
            if (updatedProvider.getCertified() != null) {
                provider.setCertified(updatedProvider.getCertified());
            }
            return providerRepository.save(provider);
        } else {
            throw new RuntimeException("Provider not found with id: " + id);
        }
    }

    @Transactional
    public ProviderStats getProviderStats(Long providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));

        long contentCount = provider.getRecipe().size()
                + provider.getMealplan().size()
                + provider.getMealkit().size();

        long subscriptionCount = subscriptionRepository.findByRecipe_Provider_Id(providerId).size();

        long saveCount = saveRepository.findByRecipe_Provider_Id(providerId).size();

        long reviewCount = provider.getReview().size();

        double averageRating = provider.getReview().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return new ProviderStats(averageRating, reviewCount, subscriptionCount, contentCount, saveCount);
    }

    @Transactional
    public List<UploadSummary> getRecentUploads(Long providerId, int limit) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));

        List<UploadSummary> uploads = new ArrayList<>();
        provider.getRecipe().forEach(r -> uploads.add(new UploadSummary("Recipe", r.getTitle(), r.getRecipeId())));
        provider.getMealplan().forEach(m -> uploads.add(new UploadSummary("Mealplan", m.getTitle(), m.getId())));
        provider.getMealkit().forEach(k -> uploads.add(new UploadSummary("Mealkit", k.getTitle(), k.getId())));

        return uploads.stream()
                .sorted(Comparator.comparing(UploadSummary::getId).reversed())
                .limit(limit)
                .toList();
    }

    @Transactional
    public List<Review> getRecentReviews(Long providerId, int limit) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));

        return provider.getReview().stream()
                .sorted(Comparator.comparing(Review::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .toList();
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

    public InputStream getProviderImageStreamInsideTx(Long providerId) {
        Provider provider = providerRepository.findById(providerId).orElse(null);
        try {
            if (provider != null && provider.getProfilePicture() != null) {
                return provider.getProfilePicture().getBinaryStream();
            } else {
                ClassPathResource defaultImage = new ClassPathResource("static/images/provider-default.jpg");
                return defaultImage.getInputStream();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving picture for provider with id: " + providerId, e);
        }
    }

    @Transactional
    public void saveProviderProfilePicture(Long providerId, InputStream profilePictureStream) {
        Provider provider = providerRepository.findById(providerId)
            .orElseThrow(() -> new RuntimeException("Provider not found with id:" + providerId));
        try {
            Blob profilePictureBlob = new javax.sql.rowset.serial.SerialBlob(profilePictureStream.readAllBytes());
            provider.setProfilePicture(profilePictureBlob);
            providerRepository.save(provider);
        } catch (Exception e) {
            throw new RuntimeException("Error saving profile picture for provider with id: " + providerId, e);
        }
    }


}
