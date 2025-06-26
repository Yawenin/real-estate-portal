// src/main/java/com/real_estate_portal/service/ListingServiceImpl.java
package com.real_estate_portal.service;

import com.real_estate_portal.model.Listing;
import com.real_estate_portal.model.User;
import com.real_estate_portal.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    // Ścieżka względem katalogu głównego projektu
    private final String uploadDir = "uploads/";

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    public Listing findById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    @Override
    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    @Override
    public void delete(Long id) {
        listingRepository.deleteById(id);
    }

    @Override
    public List<Listing> findByUser(User user) {
        return listingRepository.findByUser(user);
    }

    @Override
    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // Utwórz katalog uploads jeśli nie istnieje
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Utworzono katalog: " + uploadPath.toAbsolutePath());
            }

            // Generowanie unikalnej nazwy pliku
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(fileName);

            // Zapisanie pliku z nadpisaniem jeśli istnieje
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Zapisano plik: " + filePath.toAbsolutePath());

            // Zwróć ścieżkę URL
            return "/uploads/" + fileName;

        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania pliku: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas zapisywania pliku: " + e.getMessage());
        }
    }

    @Override
    public List<Listing> searchListings(String location, BigDecimal minPrice, BigDecimal maxPrice, Listing.ListingType listingType) {
        return listingRepository.findListingsWithFilters(location, minPrice, maxPrice, listingType);
    }
}
