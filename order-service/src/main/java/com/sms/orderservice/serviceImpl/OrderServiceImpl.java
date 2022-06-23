package com.sms.orderservice.serviceImpl;

import com.sms.orderservice.dto.InventoryResponse;
import com.sms.orderservice.dto.OrderLineItemsDto;
import com.sms.orderservice.dto.OrderRequest;
import com.sms.orderservice.model.Order;
import com.sms.orderservice.model.OrderLineItems;
import com.sms.orderservice.repository.OrderRepository;
import com.sms.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call Inventory service, and place order if product is in stock
       InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

       if (allProductsInStock){
           orderRepository.save(order);
       } else {
           throw new IllegalArgumentException("Product is not in stock, please try again later");
       }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }

    @Override
    public List<OrderLineItemsDto> getAllOrders() {
        List<Order> products = orderRepository.findAll();
        return products.stream().map(this::mapToOrderLineItems).toList();
    }

    private OrderLineItemsDto mapToOrderLineItems(Order order){
        return OrderLineItemsDto.builder()
                .id(order.getId())
//                .skuCode(order.getOrderLineItemsList())
//                .price(order.get())
//                .price(order.getPrice())
                .build();
    }
}
