package com.example.RentalAdsBoard.dao.pageDao;

import com.example.RentalAdsBoard.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface AdPageDao extends JpaRepository<Ad, Integer> {

    Page<Ad> findAll(Pageable pageable);
    Page<Ad> findByUserId(Integer userId, Pageable pageable);

}

