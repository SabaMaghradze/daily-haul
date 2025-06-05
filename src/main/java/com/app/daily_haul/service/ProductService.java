package com.app.daily_haul.service;

import com.app.daily_haul.dto.ProductRequest;
import com.app.daily_haul.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long productId, ProductRequest productRequest);

    boolean deleteProduct(Long productId);

    List<ProductResponse> searchProducts(String keyword);
}
