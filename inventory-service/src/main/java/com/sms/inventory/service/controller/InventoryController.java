package com.sms.inventory.service.controller;

import com.sms.inventory.service.dto.InventoryResponse;
import com.sms.inventory.service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService iInventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
       return iInventoryService.isInStock(skuCode);
    }
}
