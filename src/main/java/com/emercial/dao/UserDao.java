package com.emercial.dao;


import com.emercial.entities.UserEntity;

public interface UserDao {
    UserEntity selectByPrimaryKey(Integer id);
    void insertUser(UserEntity userEntity);
    UserEntity selectByTelephone(String telphone);
}
