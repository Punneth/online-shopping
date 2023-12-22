package com.inventoryservice.service.impl;

import com.inventoryservice.dao.InventoryDao;
import com.inventoryservice.dto.InventoryResponse;
import com.inventoryservice.pojo.Inventory;
import com.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        List<Inventory> inventoryList = inventoryDao.findInventoryBySkuCode(skuCode);
        if (null == inventoryList) {
            // TODO need implement Exception handler
        }
        List<InventoryResponse> inventoryResponses = inventoryList.stream().map(inventory ->
            InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
        ).toList();

        return inventoryResponses;
    }
}
