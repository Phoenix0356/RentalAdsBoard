package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdDao {
    public Ad getById(Integer adId){
        Transaction transaction=null;
        Ad ad=null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            ad=session.get(Ad.class,adId);
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            throw e;
        }
        return ad;
    }
    public List<Ad> getUserADs(Integer userId){
        Transaction transaction=null;
        List<Ad> list=null;
        try (Session session=HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            String hql="FROM Ad WHERE userId=:id";
            Query<Ad> query = session.createQuery(hql, Ad.class);
            query.setParameter("id",userId);
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
    public List<Ad> getAdsList(){
        Transaction transaction=null;
        List<Ad> list=null;
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            String hql="FROM Ad ORDER BY adId DESC";
            Query<Ad> query= session.createQuery(hql,Ad.class);
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

    public List<Ad> getAdsFromIndex(int startNumber,int adsNumber){
        Transaction transaction=null;
        List<Ad> list=null;
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            String hql="FROM Ad ORDER BY adId DESC";
            Query<Ad> query= session.createQuery(hql,Ad.class);
            query.setFirstResult(startNumber);
            query.setMaxResults(adsNumber);
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
