package com.inventoryservice.dao;

import com.inventoryservice.pojo.Inventory;

import java.util.List;

public interface InventoryDao {
    List<Inventory> findInventoryBySkuCode(List<String> skuCode);
}
