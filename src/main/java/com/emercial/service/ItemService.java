package com.emercial.service;

import com.emercial.error.BusinessException;
import com.emercial.service.dto.ItemModel;

import java.util.List;

public interface ItemService {
    //create item
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //see items
    List<ItemModel> listItem();

    //see details
    ItemModel getItemById(Integer id);

    //decrease stock
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    List<ItemModel> searchItem(String keyword);
}
