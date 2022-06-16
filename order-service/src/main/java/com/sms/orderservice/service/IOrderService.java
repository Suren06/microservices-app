package com.sms.orderservice.service;

import com.sms.orderservice.dto.OrderRequest;

public interface IOrderService {

    public void placeOrder(OrderRequest orderRequest);
}
