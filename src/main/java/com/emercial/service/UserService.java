package com.emercial.service;

import com.emercial.error.BusinessException;
import com.emercial.service.dto.UserModel;

public interface UserService {
    //get user object by id
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;

}
