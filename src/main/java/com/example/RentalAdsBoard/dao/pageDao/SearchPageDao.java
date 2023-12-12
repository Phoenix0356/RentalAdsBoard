package com.example.RentalAdsBoard.dao.pageDao;



import com.example.RentalAdsBoard.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchPageDao extends JpaRepository<Ad, String> {
    @Query("SELECT a FROM Ad a WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%',:Key,'%')) OR " +
            "LOWER(a.description) LIKE LOWER(CONCAT('%',:Key,'%')) OR " +
            "LOWER(a.address) LIKE LOWER(CONCAT('%',:Key,'%'))")
    Page<Ad> searchAds(@Param("Key") String Key, Pageable pageable);
}
