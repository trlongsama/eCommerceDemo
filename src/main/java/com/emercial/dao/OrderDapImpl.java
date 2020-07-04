package com.emercial.dao;


import com.emercial.HibernateSessionFactory;
import com.emercial.entities.OrderInfoEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDapImpl implements OrderDao{
    @Override
    public void insertOrder(OrderInfoEntity orderInfoEntity) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(orderInfoEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
