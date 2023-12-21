package com.example.RentalAdsBoard.dao;

import com.example.RentalAdsBoard.entity.Chat;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("prototype")
@Transactional
public class ChatDao {
    public List<Chat> getHistoryMessageList(Integer userId, Integer targetUserId){

        try (Session session= HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Chat WHERE (userFrom = :from AND userTo = :to) OR (userFrom = :to AND userTo = :from)";

            Query<Chat> query = session.createQuery(hql, Chat.class);

            query.setParameter("from", userId);
            query.setParameter("to", targetUserId);

            return query.list();

        }

    }

    public List<Chat>getHistorySendChats(Integer userSent){

        try (Session session= HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Chat WHERE userFrom = :name ";

            Query<Chat> query = session.createQuery(hql, Chat.class);
            query.setParameter("name", userSent);

            return query.list();

        }

    }

    public List<Chat> getHistoryReceiveChats(Integer userReceived){

        try (Session session= HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Chat WHERE userTo = :name";

            Query<Chat> query = session.createQuery(hql, Chat.class);
            query.setParameter("name", userReceived);

            return query.list();

        }

    }

    public Chat getLatestCHat(Integer userSent,Integer userReceived){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Chat where (userFrom =: from AND userTo =: to) OR (userFrom =: to AND userTo =: from) ORDER BY chatId DESC";
            Query<Chat> query = session.createQuery(hql, Chat.class);
            query.setParameter("from",userSent);
            query.setParameter("to",userReceived);
            query.setMaxResults(1);
            return query.uniqueResult();

        }
    }

}
