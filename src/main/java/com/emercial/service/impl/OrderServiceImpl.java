package com.emercial.service.impl;

import com.emercial.dao.OrderDao;
import com.emercial.entities.OrderInfoEntity;
import com.emercial.error.BusinessException;
import com.emercial.error.EmBusinessError;
import com.emercial.service.ItemService;
import com.emercial.service.OrderService;
import com.emercial.service.UserService;
import com.emercial.service.dto.ItemModel;
import com.emercial.service.dto.OrderModel;
import com.emercial.service.dto.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDao orderDao;



    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //validate order status
        ItemModel itemModel = itemService.getItemById(itemId);

        if(itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "item does not exist");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "user does not exist");
        }

        if(amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "amount is not valid");
        }
        //order - inventory
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //order into db
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //generate order id
        orderModel.setId(generateOrderNo());
        OrderInfoEntity orderInfoEntity = convertFromOrderModel(orderModel);
        orderDao.insertOrder(orderInfoEntity);

        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo() {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        Random random = new Random();

        for(int i = 0; i < 5; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    private OrderInfoEntity convertFromOrderModel(OrderModel orderModel) {
        if(orderModel == null) {
            return null;
        }
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        BeanUtils.copyProperties(orderModel, orderInfoEntity);
        return orderInfoEntity;
    }
}
