package com.sms.orderservice.service;

import com.sms.orderservice.dto.OrderLineItemsDto;
import com.sms.orderservice.dto.OrderRequest;

import java.util.List;

public interface IOrderService {

    public void placeOrder(OrderRequest orderRequest);

    List<OrderLineItemsDto> getAllOrders();
}
