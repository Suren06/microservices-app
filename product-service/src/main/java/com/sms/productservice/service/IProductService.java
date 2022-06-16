package com.sms.productservice.service;

import com.sms.productservice.dto.ProductRequest;
import com.sms.productservice.dto.ProductResponse;

import java.util.List;

public interface IProductService {

    public void createProduct(ProductRequest productRequest);

    public List<ProductResponse> getAllProducts();

}
