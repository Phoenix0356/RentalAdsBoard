package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Picture;
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
public class PictureDao {

    public Picture getPictureById(Integer pictureId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Picture.class, pictureId);
        } catch (Exception e) {
            throw e;
        }
    }

    public Picture getFirstPicture(Integer adId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Picture WHERE adId = :adId ORDER BY pictureId ASC";
            Query<Picture> query = session.createQuery(hql, Picture.class);
            query.setParameter("adId", adId);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Picture> getAdPictureList(Integer adId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Picture WHERE adId = :id ORDER BY pictureId ASC";
            Query<Picture> query = session.createQuery(hql, Picture.class);
            query.setParameter("id", adId);
            return query.list();
        } catch (Exception e) {
            throw e;
        }
    }
}
