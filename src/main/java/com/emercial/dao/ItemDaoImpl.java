package com.emercial.dao;

import com.emercial.HibernateSessionFactory;
import com.emercial.entities.ItemEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @Override
    public ItemEntity selectByPrimaryKey(Integer id) {
        ItemEntity itemEntity = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ItemEntity> query = session.createQuery("from ItemEntity where id=" + id, ItemEntity.class);
            itemEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return itemEntity;
    }

    @Override
    public void insertItem(ItemEntity itemEntity) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(itemEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ItemEntity> listItem() {
        List<ItemEntity> result = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("from ItemEntity order by sales DESC");
            result = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void updateSale(int amount, int id) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            ItemEntity itemStockEntity = this.selectByPrimaryKey(id);
            int new_sale = itemStockEntity.getSales() + amount;
            Query query = session.createQuery("update ItemEntity I set I.sales = :para1  where id = :para2");
            query.setParameter("para1", new_sale);
            query.setParameter("para2", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ItemEntity> searchByKeyword(String keyword) {
        List<ItemEntity> result = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("from ItemEntity I where I.title like :para order by sales DESC");
            query.setParameter("para", "%"+keyword+"%");
            result = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}
