package com.emercial.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.emercial.dao.UserDao;
import com.emercial.dao.UserPasswordDao;
import com.emercial.entities.UserEntity;
import com.emercial.entities.UserPasswordEntity;
import com.emercial.error.BusinessException;
import com.emercial.error.EmBusinessError;
import com.emercial.service.UserService;
import com.emercial.service.dto.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    /*@Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;*/

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPasswordDao userPasswordDao;

    @Override
    public UserModel getUserById(Integer id) {
        try{
            UserEntity userEntity = userDao.selectByPrimaryKey(id);
            if (userEntity==null) {
            return null;
        }
            UserPasswordEntity userPasswordEntity = userPasswordDao.selectByUserId(userEntity.getId());
            return convertFromDataObject(userEntity, userPasswordEntity);
        }catch(Exception e){
            System.out.println(e);;
        }
        return null;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(userModel.getName())
                ||userModel.getGender()==null
                ||StringUtils.isEmpty(userModel.getTelnumber())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //model -> dataobject
        UserEntity userEntity = convertFromModel(userModel);
        try {
            userDao.insertUser(userEntity);
        }catch (Exception e){
            System.out.println(e);
        }

        userModel.setId(userEntity.getId());
        UserPasswordEntity userPasswordEntity = convertPasswordFromModel(userModel);
        try {
            userPasswordDao.insertUserpassword(userPasswordEntity);
        }catch (Exception e){
            System.out.println(e);
        }
        return;
    }

    private  UserPasswordEntity convertPasswordFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
        userPasswordEntity.setEncriptPassword(userModel.getEncryptPassword());
        userPasswordEntity.setUserId(userModel.getId());
            return userPasswordEntity;
    }

    private UserEntity convertFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);

        return userEntity;
    }


    private UserModel convertFromDataObject(UserEntity userEntity, UserPasswordEntity userPasswordEntity){
        if (userEntity == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userEntity, userModel);
        if (userPasswordEntity != null){
            userModel.setEncryptPassword(userPasswordEntity.getEncriptPassword());
        }
        return userModel;
    }

    @Override
    public UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        //get user_info by telephone
        UserEntity userEntity = userDao.selectByTelephone(telephone);

        if(userEntity == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordEntity userPasswordEntity = userPasswordDao.selectByUserId(userEntity.getId());
        UserModel userModel = convertFromDataObject(userEntity, userPasswordEntity);

        //match password with encoded password
        if(!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }
}
