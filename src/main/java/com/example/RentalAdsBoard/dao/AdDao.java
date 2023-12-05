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

    public List<Ad> getUserADs(Integer userId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Ad WHERE userId = :id";
            Query<Ad> query = session.createQuery(hql, Ad.class);
            query.setParameter("id", userId);
            return query.list();
        }
    }

    public List<Ad> getAdsList(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Ad ORDER BY adId DESC";
            Query<Ad> query = session.createQuery(hql, Ad.class);
            return query.list();
        }
    }

    public List<Ad> getAdsFromIndex(int startNumber, int adsNumber){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Ad ORDER BY adId DESC";
            Query<Ad> query = session.createQuery(hql, Ad.class);
            query.setFirstResult(startNumber);
            query.setMaxResults(adsNumber);
            return query.list();
        }
    }
}
