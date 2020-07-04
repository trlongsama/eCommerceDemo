package com.emercial.controller;

import com.emercial.error.BusinessException;
import com.emercial.error.EmBusinessError;
import com.emercial.response.CommonReturnType;
import com.emercial.service.OrderService;
import com.emercial.service.dto.OrderModel;
import com.emercial.service.dto.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    //create order
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId") Integer itemId,
                                        @RequestParam(name="amount") Integer amount) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "user not login can not create order");
        }

        //get user info
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, amount);

        return CommonReturnType.create(null);
    }

}
