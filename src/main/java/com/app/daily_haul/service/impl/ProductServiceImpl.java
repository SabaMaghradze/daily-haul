package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.ProductRequest;
import com.app.daily_haul.dto.ProductResponse;
import com.app.daily_haul.model.Product;
import com.app.daily_haul.repository.ProductRepository;
import com.app.daily_haul.service.ProductService;
import com.app.daily_haul.utils.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        Mappers.productReqToEnt(product, productRequest);
        Product saved = productRepository.save(product);
        return Mappers.getProductResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(prod -> Mappers.getProductResponse(prod))
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        return productRepository.findById(productId)
                .map(daProd -> {
                    Mappers.productReqToEnt(daProd, productRequest);
                    productRepository.save(daProd);
                    return Mappers.getProductResponse(daProd);
                }).orElse(null);
    }

    @Override
    public boolean deleteProduct(Long productId) {
        return productRepository.findById(productId)
                .map(prod -> {
                    prod.setActive(false);
                    productRepository.save(prod);
                    return true;
                }).orElse(false);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(Mappers::getProductResponse)
                .collect(Collectors.toList());
    }
}
