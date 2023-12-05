package com.example.RentalAdsBoard.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.BaseEntity;
import com.example.RentalAdsBoard.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope("prototype")
@Transactional
public class BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public Integer save(T entity) {
        entityManager.persist(entity);
        entityManager.flush(); // 确保ID被回填到实体中
        return getIdFromEntity(entity);
    }

    private Integer getIdFromEntity(T entity) {
        if (entity instanceof BaseEntity) {
            return ((BaseEntity) entity).getId();
        }
        return null; // 或抛出异常
    }


    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public T findById(Class<T> clazz, Object id) {
        return entityManager.find(clazz, id);
    }
    
}
