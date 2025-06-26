package com.real_estate_portal.repository;

// src/main/java/com/realestate/portal/repository/ListingRepository.java

import com.real_estate_portal.model.Listing;
import com.real_estate_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUser(User user);
    List<Listing> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT l FROM Listing l WHERE " +
            "(:location IS NULL OR LOWER(l.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:minPrice IS NULL OR l.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR l.price <= :maxPrice) AND " +
            "(:listingType IS NULL OR l.listingType = :listingType)")
    List<Listing> findListingsWithFilters(
            @Param("location") String location,
            @Param("minPrice") java.math.BigDecimal minPrice,
            @Param("maxPrice") java.math.BigDecimal maxPrice,
            @Param("listingType") Listing.ListingType listingType
    );
}
