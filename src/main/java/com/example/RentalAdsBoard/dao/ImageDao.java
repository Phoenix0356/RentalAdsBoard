package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Image;
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
public class ImageDao {

    public Image getImageById(Integer imageId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Image.class, imageId);
        } catch (Exception e) {
            throw e;
        }
    }

    public Image getAdFirstImage(Integer adId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Image WHERE adId = :adId ORDER BY imageId ASC";
            Query<Image> query = session.createQuery(hql, Image.class);
            query.setParameter("adId", adId);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Image> getAdImageList(Integer adId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Image WHERE adId = :id ORDER BY imageId ASC";
            Query<Image> query = session.createQuery(hql, Image.class);
            query.setParameter("id", adId);
            return query.list();
        } catch (Exception e) {
            throw e;
        }
    }
}
