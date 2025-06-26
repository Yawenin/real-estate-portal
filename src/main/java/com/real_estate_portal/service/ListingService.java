// src/main/java/com/real_estate_portal/service/ListingService.java
package com.real_estate_portal.service;

import com.real_estate_portal.model.Listing;
import com.real_estate_portal.model.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.math.BigDecimal;

public interface ListingService {
    List<Listing> findAll();
    Listing findById(Long id);
    Listing save(Listing listing);
    void delete(Long id);
    List<Listing> findByUser(User user);
    String saveImage(MultipartFile file);
    List<Listing> searchListings(String location, BigDecimal minPrice, BigDecimal maxPrice, Listing.ListingType listingType);
}
