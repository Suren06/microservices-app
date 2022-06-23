package com.sms.orderservice.controller;

import com.sms.orderservice.dto.OrderLineItemsDto;
import com.sms.orderservice.dto.OrderRequest;
import com.sms.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    public final IOrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Ordered successfully..";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderLineItemsDto> getAllOrders(){
        return orderService.getAllOrders();
    }
}
