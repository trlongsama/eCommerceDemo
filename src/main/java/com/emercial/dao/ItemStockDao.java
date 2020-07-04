package com.emercial.dao;

import com.emercial.entities.ItemStockEntity;

public interface ItemStockDao {
    void insertStock(ItemStockEntity itemStockEntity);
    ItemStockEntity selectByItemId(int id);
    int decreaseStock(int itemId, int amount);
}
