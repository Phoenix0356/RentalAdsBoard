package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Scope("prototype")
@Transactional
public class AdDao {

    public Ad getById(Integer adId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Ad.class, adId);
        }
    }
}
