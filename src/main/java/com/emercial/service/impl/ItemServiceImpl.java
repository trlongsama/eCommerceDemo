package com.emercial.service.impl;

import com.emercial.dao.ItemDao;
import com.emercial.dao.ItemStockDao;
import com.emercial.entities.ItemEntity;
import com.emercial.entities.ItemStockEntity;
import com.emercial.error.BusinessException;
import com.emercial.error.EmBusinessError;
import com.emercial.service.ItemService;
import com.emercial.service.dto.ItemModel;
import com.emercial.validator.ValidationResult;
import com.emercial.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemStockDao itemStockDao;


    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //validate parameters
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //convert item_model -> data object
        ItemEntity itemEntity = this.convertItemDOFromItemModel(itemModel);

        //write to database
        try {
            itemDao.insertItem(itemEntity);
        }catch (Exception e){
            System.out.println(e);
        }
        itemModel.setId(itemEntity.getId());

        ItemStockEntity itemStockEntity = this.convertItemStockFromItemModel(itemModel);

        itemStockDao.insertStock(itemStockEntity);
        //return the object that is created -> upstream to know the created object

        return this.getItemById(itemModel.getId());
    }

    private ItemStockEntity convertItemStockFromItemModel(ItemModel itemModel) {
        if(itemModel == null) {
            return null;
        }

        ItemStockEntity itemStockEntity = new ItemStockEntity();
        itemStockEntity.setItemId(itemModel.getId());
        itemStockEntity.setStock(itemModel.getStock());

        return itemStockEntity;
    }

    private ItemEntity convertItemDOFromItemModel(ItemModel itemModel) {
        if(itemModel == null) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity();
        BeanUtils.copyProperties(itemModel, itemEntity);
        itemEntity.setPrice(itemModel.getPrice().doubleValue());
        return itemEntity;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemEntity> itemEntities = itemDao.listItem();
        List<ItemModel> itemModelList = itemEntities.stream().map(itemEntity ->  {
            ItemStockEntity itemStockEntity = itemStockDao.selectByItemId(itemEntity.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemEntity, itemStockEntity);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemEntity itemEntity = itemDao.selectByPrimaryKey(id);
        if(itemEntity == null) {
            return null;
        }
        //get stock
        ItemStockEntity itemStockEntity = itemStockDao.selectByItemId(itemEntity.getId());

        //dataobject -> model
        ItemModel itemModel = convertModelFromDataObject(itemEntity, itemStockEntity);

        return itemModel;
    }

    private ItemModel convertModelFromDataObject(ItemEntity itemEntity, ItemStockEntity itemStockEntity) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemEntity, itemModel);
        itemModel.setPrice(new BigDecimal(itemEntity.getPrice()));
        itemModel.setStock(itemStockEntity.getStock());
        return itemModel;
    }
    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow = itemStockDao.decreaseStock(itemId, amount);
        itemDao.updateSale(amount, itemId);
        if(affectedRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ItemModel> searchItem(String keyword) {
        List<ItemEntity> itemEntities = itemDao.searchByKeyword(keyword);
        List<ItemModel> itemModelList = itemEntities.stream().map(itemEntity ->  {
            ItemStockEntity itemStockEntity = itemStockDao.selectByItemId(itemEntity.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemEntity, itemStockEntity);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

}
