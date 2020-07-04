package com.emercial.dao;


import com.emercial.entities.ItemEntity;

import java.util.List;

public interface ItemDao {
    ItemEntity selectByPrimaryKey(Integer id);
    void insertItem(ItemEntity itemEntity);
    List<ItemEntity> listItem();
    void updateSale(int amount, int id);
    List<ItemEntity> searchByKeyword(String keyword);
}
