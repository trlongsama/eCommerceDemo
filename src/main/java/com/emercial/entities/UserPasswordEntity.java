package com.emercial.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_password", schema = "ecommercial", catalog = "")
public class UserPasswordEntity {
    private Integer userId;
    private String encriptPassword;

    @Id
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "encript_password", nullable = false, length = 45)
    public String getEncriptPassword() {
        return encriptPassword;
    }

    public void setEncriptPassword(String encriptPassword) {
        this.encriptPassword = encriptPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPasswordEntity that = (UserPasswordEntity) o;

        if (userId != that.userId) return false;
        if (encriptPassword != null ? !encriptPassword.equals(that.encriptPassword) : that.encriptPassword != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (encriptPassword != null ? encriptPassword.hashCode() : 0);
        return result;
    }
}
