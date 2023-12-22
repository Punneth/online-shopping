package com.orderservice.service.impl;

import com.google.gson.Gson;
import com.orderservice.constansts.ErrorCodeEnum;
import com.orderservice.dao.OrderDao;
import com.orderservice.dto.InventoryResponse;
import com.orderservice.dto.OrderLineItemsDTO;
import com.orderservice.dto.OrderRequest;
import com.orderservice.exception.OrderException;
import com.orderservice.http.HttpRequest;
import com.orderservice.http.HttpRestTemplateEngine;
import com.orderservice.pojo.ErrorResponse;
import com.orderservice.pojo.Order;
import com.orderservice.pojo.OrderLineItems;
import com.orderservice.pojo.OrderResponse;
import com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private HttpRestTemplateEngine httpRestTemplateEngine;

    @Value("${inventory.processing.service.check.isinstock}")
    private String isInStockUrl;

    @Override
    public OrderResponse placeOrders(OrderRequest orderRequest) {

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOS().
                stream().map(orderLineItemsDTO -> mapToDto(orderLineItemsDTO)).toList();

        String orderNumber = UUID.randomUUID().toString();
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .orderLineItems(orderLineItems)
                .build();

        List<String> skuCodeList = orderLineItems.stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();

        String baseUrl = UriComponentsBuilder.fromUriString(isInStockUrl)
                .queryParam("skuCode", skuCodeList.toArray()).build().toUriString();

        HttpRequest httpRequest = HttpRequest.builder().url(baseUrl).httpMethod(HttpMethod.GET).build();

        ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);

        Gson gson = new Gson();

        if (null == response || null == response.getBody()) {

            throw new OrderException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCodeEnum.FAILED_TO_CREATE_ORDER.getErrorCode(),
                    ErrorCodeEnum.FAILED_TO_CREATE_ORDER.getErrorMessage());
        }

        if (200 != response.getStatusCodeValue()) {
            ErrorResponse errorResponse = gson.fromJson(response.getBody(), ErrorResponse.class);
            throw new OrderException(HttpStatus.BAD_GATEWAY,
                    errorResponse.getErrorCode(),
                    errorResponse.getErrorMessage());
        }

        InventoryResponse[] inventoryResponseArray = gson.fromJson(response.getBody(), InventoryResponse[].class);

        if (null == inventoryResponseArray) {
            throw new OrderException(HttpStatus.BAD_REQUEST,
                    ErrorCodeEnum.ITEM_NOT_EXIST.getErrorCode(),
                    ErrorCodeEnum.ITEM_NOT_EXIST.getErrorMessage());
        }
        boolean result =
                Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if (!result) {

            throw new OrderException(HttpStatus.BAD_REQUEST,
                    ErrorCodeEnum.ITEM_NOT_EXIST.getErrorCode(),
                    ErrorCodeEnum.ITEM_NOT_EXIST.getErrorMessage());
        }
        orderDao.save(order);
        return OrderResponse.builder().orderNumber(orderNumber).successMessage("Order placed successfully").build();
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = OrderLineItems.builder()
                .price(orderLineItemsDTO.getPrice())
                .quantity(orderLineItemsDTO.getQuantity())
                .skuCode(orderLineItemsDTO.getSkuCode())
                .build();
        return orderLineItems;
    }
}
