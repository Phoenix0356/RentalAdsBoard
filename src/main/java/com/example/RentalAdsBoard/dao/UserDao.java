package com.example.RentalAdsBoard.dao;
import com.example.RentalAdsBoard.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {
    public User getById(Integer userId){
        Transaction transaction = null;
        User user=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class,userId);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }

    public void save(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}


