package com.emercial.dao;

import com.emercial.HibernateSessionFactory;
import com.emercial.entities.ItemStockEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ItemStockDaoImpl implements ItemStockDao{
    @Override
    public void insertStock(ItemStockEntity itemStockEntity) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(itemStockEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public ItemStockEntity selectByItemId(int id) {
        ItemStockEntity itemStockEntity = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ItemStockEntity> query = session.createQuery("from ItemStockEntity where itemId=" + id, ItemStockEntity.class);
            itemStockEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return itemStockEntity;
    }

    @Override
    public int decreaseStock(int itemId, int amount) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        int new_stock = 0;
        try {
            ItemStockEntity itemStockEntity = this.selectByItemId(itemId);
            new_stock = itemStockEntity.getStock() - amount;
            Query query = session.createQuery("update ItemStockEntity I set I.stock = :para1  where id = :para2");
            query.setParameter("para1", new_stock);
            query.setParameter("para2", itemId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new_stock;
    }

}
