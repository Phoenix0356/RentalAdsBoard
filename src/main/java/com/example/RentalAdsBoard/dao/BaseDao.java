package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope("prototype")
@Transactional
public class BaseDao<T> {

    public Integer save(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Integer) session.save(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.update(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.delete(entity);
        } catch (Exception e) {
            throw e;
        }
    }
}
