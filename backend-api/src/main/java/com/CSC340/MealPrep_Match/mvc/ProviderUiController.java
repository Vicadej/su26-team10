package com.CSC340.MealPrep_Match.mvc;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.CSC340.MealPrep_Match.entity.Provider;
import com.CSC340.MealPrep_Match.entity.Customer;
import com.CSC340.MealPrep_Match.entity.Mealplan;
import com.CSC340.MealPrep_Match.service.CustomerService;
import com.CSC340.MealPrep_Match.service.MealkitService;
import com.CSC340.MealPrep_Match.service.MealplanService;
import com.CSC340.MealPrep_Match.service.ProviderService;
import com.CSC340.MealPrep_Match.service.RecipeService;
import com.CSC340.MealPrep_Match.service.ReviewService;
import com.CSC340.MealPrep_Match.service.SaveService;
import com.CSC340.MealPrep_Match.service.SubscriptionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/provider")
public class ProviderUiController {
    private final ProviderService providerService;
    private final RecipeService recipeService;
    private final MealplanService mealplanService;
    private final MealkitService mealkitService;
    private final ReviewService reviewService;
    private final SaveService saveService;
    private final SubscriptionService subscriptionService;

    private final TransactionTemplate transactionTemplate;

    public ProviderUiController(ProviderService providerService, RecipeService recipeService,
            MealplanService mealplanService, MealkitService mealkitService, ReviewService reviewService,
            SaveService saveService, SubscriptionService subscriptionService, TransactionTemplate transactionTemplate) {
        this.providerService = providerService;
        this.recipeService = recipeService;
        this.mealplanService = mealplanService;
        this.mealkitService = mealkitService;
        this.reviewService = reviewService;
        this.saveService = saveService;
        this.subscriptionService = subscriptionService;
        this.transactionTemplate = transactionTemplate;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/provider-registration";
    }

    @PostMapping("/signup")
    public String registerProvider(Provider provider, MultipartFile profilePictureFile, HttpSession session) {
        Provider created = providerService.createProvider(provider);
        try {
            providerService.saveProviderProfilePicture(created.getId(), profilePictureFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("providerId", created.getId());
        return "redirect:/provider/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "provider/provider-login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String email, @RequestParam String password) {
        Provider provider = providerService.findByEmail(email);
        if (provider != null && password.equals(provider.getPassword())) {
            session.setAttribute("providerId", provider.getId());
            return "redirect:/provider/dashboard";
        }
        return "redirect:/provider/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long providerId = (Long) session.getAttribute("providerId");
        if (providerId == null) {
            return "redirect:/provider/login";
        }

        Provider provider = providerService.findById(providerId);
        model.addAttribute("provider", provider);
        model.addAttribute("stats", providerService.getProviderStats(providerId));
        model.addAttribute("recentUploads", providerService.getRecentUploads(providerId, 5));
        model.addAttribute("recentReviews", providerService.getRecentReviews(providerId, 5));

        return "provider/provider-dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long providerId = (Long) session.getAttribute("providerId");
        if (providerId == null) {
            return "redirect:/provider/login";
        }

        Provider provider = providerService.findById(providerId);
        model.addAttribute("provider", provider);

        return "/provider/provider-profile";
    }

    @GetMapping("/profile/edit")
    public String editProfileInformation(HttpSession session, Model model) {
        Long providerId = (Long) session.getAttribute("providerId");
        if (providerId == null) {
            return "redirect:/provider/login";
        }

        Provider provider = providerService.findById(providerId);
        model.addAttribute("provider", provider);
        return "/provider/provider-profile-customization";
    }

    @PostMapping("/profile/edit")
    public String updateProfileInformation(Provider provider, MultipartFile profilePictureFile, HttpSession session) {
        Long providerId = (Long) session.getAttribute("providerId");
        if (providerId == null) {
            return "redirect:/provider/login";
        }
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            try {
                providerService.saveProviderProfilePicture(providerId, profilePictureFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        providerService.updateProviderInfo(providerId, provider);
        return "redirect:/provider/profile";
    }

    @GetMapping("/picture/{providerId}")
    public ResponseEntity<StreamingResponseBody> streamProviderImage(@PathVariable Long providerId) {

        StreamingResponseBody stream = outputStream -> {
            transactionTemplate.execute(status -> {
                try (InputStream imageStream = providerService.getProviderImageStreamInsideTx(providerId)) {
                    StreamUtils.copy(imageStream, outputStream);
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Streaming failed", e);
                }
                return null;
            });
        };

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(stream);
    }

}
