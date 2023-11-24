package com.example.RentalAdsBoard.dao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class UserDao {
    //get User after login
    public User getById(Integer userId)throws Exception{
        Transaction transaction = null;
        User user=null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class,userId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }
    //login
    public User getByUsername(String username) {
        Transaction transaction=null;
        User user=null;
        try(Session session=HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user=session.get(User.class,username);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }
//    public User save(User user) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(user);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw e;
//        }
//        return user;
//    }

//    public void delete(Integer userId){
//        Transaction transaction=null;
//        try (Session session=HibernateUtil.getSessionFactory().openSession()){
//            transaction = session.beginTransaction();
//            User user=session.get(User.class,userId);
//            session.delete(user);
//            transaction.commit();
//        }catch (Exception e){
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw e;
//        }
//    }
}


