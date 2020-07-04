package com.emercial.service;

import com.emercial.error.BusinessException;
import com.emercial.service.dto.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
