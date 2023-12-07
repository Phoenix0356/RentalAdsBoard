package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageDao extends JpaRepository<Ad,Long> {
    Page<Ad> findAll(Pageable pageable);
}
