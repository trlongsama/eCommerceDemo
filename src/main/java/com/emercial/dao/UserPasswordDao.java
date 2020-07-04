package com.emercial.dao;

import com.emercial.entities.UserPasswordEntity;

public interface UserPasswordDao {
    UserPasswordEntity selectByUserId(int id);
    void insertUserpassword(UserPasswordEntity userPasswordEntity);
}
