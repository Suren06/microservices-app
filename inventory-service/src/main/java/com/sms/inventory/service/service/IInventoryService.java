package com.sms.inventory.service.service;

import com.sms.inventory.service.dto.InventoryResponse;

import java.util.List;

public interface IInventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);
}
