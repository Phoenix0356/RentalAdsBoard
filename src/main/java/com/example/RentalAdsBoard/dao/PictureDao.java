package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.Picture;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Scope("prototype")
public class PictureDao {
    public Picture getPictureById(Integer pictureId){
        Transaction transaction=null;
        Picture picture;
        try (Session session=HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            picture=session.get(Picture.class,pictureId);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return picture;
    }
    public Picture getFirstPicture(Integer adId){
        Transaction transaction=null;
        Picture picture;
        try (Session session=HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            String hql = "FROM Picture WHERE adId = :adId ORDER BY pictureId ASC";
            Query<Picture> query = session.createQuery(hql, Picture.class);
            query.setParameter("adId", adId);
            query.setMaxResults(1);
            picture =  query.uniqueResult();
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return picture;
    }


    public List<Picture> getAdPictureList(Integer adId){
        Transaction transaction=null;
        List<Picture> list;
        try (Session session= HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            String hql="FROM Picture WHERE adId=:id";
            Query<Picture> query=session.createQuery(hql, Picture.class);
            query.setParameter("id",adId);
            list=query.list();
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            throw e;
        }
        return list;
    }
}
