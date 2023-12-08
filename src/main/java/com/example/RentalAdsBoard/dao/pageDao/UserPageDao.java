package com.example.RentalAdsBoard.dao.pageDao;

import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPageDao extends JpaRepository<User,Integer> {
    Page<User> findAll(Pageable pageable);
}
