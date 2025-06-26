package com.real_estate_portal.controller;

import com.real_estate_portal.model.Listing;
import com.real_estate_portal.model.User;
import com.real_estate_portal.service.ListingService;
import com.real_estate_portal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;
import java.util.Comparator;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private UserService userService;

    @GetMapping("/listings")
    public String listingsPage(Model model,
                               @RequestParam(required = false) String location,
                               @RequestParam(required = false) BigDecimal minPrice,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               @RequestParam(required = false) Listing.ListingType listingType) {

        List<Listing> listings;

        if (location != null || minPrice != null || maxPrice != null || listingType != null) {
            listings = listingService.searchListings(location, minPrice, maxPrice, listingType);
        } else {
            listings = listingService.findAll();
        }

        model.addAttribute("listings", listings);
        model.addAttribute("location", location);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("listingType", listingType);
        model.addAttribute("listingTypes", Listing.ListingType.values());

        return "listings";
    }

    @GetMapping("/listing/{id}")
    public String listingDetails(@PathVariable Long id, Model model) {
        Listing listing = listingService.findById(id);
        if (listing == null) {
            return "redirect:/listings";
        }
        model.addAttribute("listing", listing);
        return "listing-details";
    }

    @GetMapping("/add-listing")
    public String showAddListingForm(Model model) {
        model.addAttribute("listing", new Listing());
        model.addAttribute("listingTypes", Listing.ListingType.values());
        model.addAttribute("propertyTypes", Listing.PropertyType.values());
        return "add-listing";
    }

    @PostMapping("/add-listing")
    public String addListing(@ModelAttribute("listing") @Valid Listing listing,
                             BindingResult result,
                             @RequestParam("image") MultipartFile file,
                             Principal principal,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listingTypes", Listing.ListingType.values());
            model.addAttribute("propertyTypes", Listing.PropertyType.values());
            return "add-listing";
        }

        // Zapisanie zdjęcia
        if (!file.isEmpty()) {
            String imageUrl = listingService.saveImage(file);
            listing.setImageUrl(imageUrl);
        }

        // Przypisanie użytkownika
        User user = userService.findByEmail(principal.getName());
        listing.setUser(user);

        listingService.save(listing);
        return "redirect:/listings";
    }

    @GetMapping("/my-listings")
    public String myListings(Model model, Principal principal,
                             @RequestParam(required = false) String filterType,
                             @RequestParam(required = false) String sortBy,
                             @RequestParam(required = false) String propertyType) {

        User user = userService.findByEmail(principal.getName());
        List<Listing> allUserListings = listingService.findByUser(user);

        // Filtrowanie po typie ogłoszenia
        List<Listing> filteredListings = allUserListings;
        if (filterType != null && !filterType.equals("ALL")) {
            try {
                Listing.ListingType listingType = Listing.ListingType.valueOf(filterType);
                filteredListings = allUserListings.stream()
                        .filter(listing -> listing.getListingType() == listingType)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Nieprawidłowy typ - pokaż wszystkie
            }
        }

        // Filtrowanie po typie nieruchomości
        if (propertyType != null && !propertyType.isEmpty()) {
            try {
                Listing.PropertyType propType = Listing.PropertyType.valueOf(propertyType);
                filteredListings = filteredListings.stream()
                        .filter(listing -> listing.getPropertyType() == propType)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Nieprawidłowy typ - ignoruj filtr
            }
        }

        // Sortowanie
        if (sortBy != null) {
            switch (sortBy) {
                case "oldest":
                    filteredListings.sort(Comparator.comparing(Listing::getCreatedAt));
                    break;
                case "price-asc":
                    filteredListings.sort(Comparator.comparing(Listing::getPrice));
                    break;
                case "price-desc":
                    filteredListings.sort(Comparator.comparing(Listing::getPrice).reversed());
                    break;
                case "newest":
                default:
                    filteredListings.sort(Comparator.comparing(Listing::getCreatedAt).reversed());
                    break;
            }
        }

        // Oblicz statystyki dla wszystkich ogłoszeń użytkownika
        long totalListings = allUserListings.size();
        long saleCount = allUserListings.stream()
                .filter(listing -> listing.getListingType() != null)
                .filter(listing -> listing.getListingType() == Listing.ListingType.SALE)
                .count();

        long rentCount = allUserListings.stream()
                .filter(listing -> listing.getListingType() != null)
                .filter(listing -> listing.getListingType() == Listing.ListingType.RENT)
                .count();

        model.addAttribute("listings", filteredListings);
        model.addAttribute("totalListings", totalListings);
        model.addAttribute("saleCount", saleCount);
        model.addAttribute("rentCount", rentCount);
        model.addAttribute("filterType", filterType);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("propertyType", propertyType);

        return "my-listings";
    }


    @PostMapping("/delete-listing/{id}")
    public String deleteListing(@PathVariable Long id, Principal principal) {
        Listing listing = listingService.findById(id);
        User user = userService.findByEmail(principal.getName());

        // Sprawdzenie czy użytkownik jest właścicielem ogłoszenia
        if (listing != null && listing.getUser().getId().equals(user.getId())) {
            listingService.delete(id);
        }

        return "redirect:/my-listings";
    }
}
