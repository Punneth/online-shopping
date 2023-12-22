package com.inventoryservice.dao.impl;

import com.inventoryservice.dao.InventoryDao;
import com.inventoryservice.pojo.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class InventoryDaoImpl implements InventoryDao {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<Inventory> findInventoryBySkuCode(List<String> skuCodes) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("skuCodes", skuCodes);
        List<Inventory> inventories= namedJdbcTemplate.query(findInventoryBySkuCode(),
                parameterSource,
                new BeanPropertyRowMapper<>(Inventory.class));
        return inventories;
    }

    public String findInventoryBySkuCode() {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM t_inventory WHERE ");
        queryBuilder.append("skuCode IN (:skuCodes)");
        return queryBuilder.toString();
    }
}
