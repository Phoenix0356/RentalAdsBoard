package com.example.RentalAdsBoard.dao;



import com.example.RentalAdsBoard.entity.Chat;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("prototype")
@Transactional
public class ChatDao {
    public List<Chat> getHistoryList(String username, String targetUsername) {
        Transaction transaction = null;
        List<Chat> list;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Chat WHERE (userFrom = :from AND userTo = :to) OR (userFrom = :to AND userTo = :from)";
            Query<Chat> query = session.createQuery(hql, Chat.class);
            query.setParameter("from", username);
            query.setParameter("to", targetUsername);
            transaction.commit();
            list=query.list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;


        }
        return list;
    }

}
