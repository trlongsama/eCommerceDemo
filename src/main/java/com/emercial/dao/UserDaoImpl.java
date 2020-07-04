package com.emercial.dao;

import com.emercial.HibernateSessionFactory;
import com.emercial.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao{


    @Override
    public UserEntity selectByPrimaryKey(Integer id) {
        UserEntity userEntity = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<UserEntity> query = session.createQuery("from UserEntity where id=" + id, UserEntity.class);
            userEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userEntity;
    }

    @Override
    public void insertUser(UserEntity userEntity){
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(userEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public UserEntity selectByTelephone(String telphone) {
        UserEntity userEntity = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<UserEntity> query = session.createQuery("from UserEntity where telnumber=" + telphone, UserEntity.class);
            userEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userEntity;
    }

}
