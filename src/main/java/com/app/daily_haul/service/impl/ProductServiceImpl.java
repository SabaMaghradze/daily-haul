package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.ProductRequest;
import com.app.daily_haul.dto.ProductResponse;
import com.app.daily_haul.model.Product;
import com.app.daily_haul.repository.ProductRepository;
import com.app.daily_haul.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private ProductResponse getProductResponse(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive());
    }

    private Product productReqToEnt(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName() != null ? productRequest.getName() : product.getName());
        product.setDescription(productRequest.getDescription() != null ? productRequest.getDescription() : product.getDescription());
        product.setPrice(productRequest.getPrice() != null ? productRequest.getPrice() : product.getPrice());
        product.setQuantity(productRequest.getQuantity() != null ? productRequest.getQuantity() : product.getQuantity());
        product.setCategory(productRequest.getCategory() != null ? productRequest.getCategory() : product.getCategory());
        product.setImageUrl(productRequest.getImageUrl() != null ? productRequest.getImageUrl() : product.getImageUrl());

        return product;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        productReqToEnt(product, productRequest);
        Product saved = productRepository.save(product);
        return getProductResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(prod -> getProductResponse(prod))
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        return productRepository.findById(productId)
                .map(daProd -> {
                    productReqToEnt(daProd, productRequest);
                    productRepository.save(daProd);
                    return getProductResponse(daProd);
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
                .map(this::getProductResponse)
                .collect(Collectors.toList());
    }
}
