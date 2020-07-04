package com.emercial.entities;

import javax.persistence.*;

@Entity
@Table(name = "item_stock", schema = "ecommercial", catalog = "")
public class ItemStockEntity {
    private Integer itemId;
    private Integer stock;

    @Id
    @Column(name = "item_id", nullable = false)
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "stock", nullable = false)
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemStockEntity that = (ItemStockEntity) o;

        if (itemId != that.itemId) return false;
        if (stock != that.stock) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + stock;
        return result;
    }
}
