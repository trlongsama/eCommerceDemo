package com.emercial.entities;

import javax.persistence.*;

@Entity
@Table(name = "order", schema = "ecommercial", catalog = "")
public class OrderInfoEntity {
    private String id;
    private Integer userId;
    private Integer itemId;
    private double itemPrice;
    private Integer amount;
    private double orderPrice;

    @Id
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "item_id", nullable = false)
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "item_price", nullable = false, precision = 0)
    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "order_price", nullable = false, precision = 0)
    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInfoEntity that = (OrderInfoEntity) o;

        if (userId != that.userId) return false;
        if (itemId != that.itemId) return false;
        if (Double.compare(that.itemPrice, itemPrice) != 0) return false;
        if (amount != that.amount) return false;
        if (Double.compare(that.orderPrice, orderPrice) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + itemId;
        temp = Double.doubleToLongBits(itemPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + amount;
        temp = Double.doubleToLongBits(orderPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
