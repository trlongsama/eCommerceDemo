package com.emercial.dao;

import com.emercial.HibernateSessionFactory;
import com.emercial.entities.UserPasswordEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserPasswordDaoImpl implements UserPasswordDao{

    @Override
    public UserPasswordEntity selectByUserId(int id){
        UserPasswordEntity userPasswordEntity = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<UserPasswordEntity> query = session.createQuery("from UserPasswordEntity where userId=" + id, UserPasswordEntity.class);
            userPasswordEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userPasswordEntity;
    }

    @Override
    public void insertUserpassword(UserPasswordEntity userPasswordEntity) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(userPasswordEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
