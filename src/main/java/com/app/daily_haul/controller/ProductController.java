package com.app.daily_haul.controller;

import com.app.daily_haul.dto.ProductRequest;
import com.app.daily_haul.dto.ProductResponse;
import com.app.daily_haul.service.ProductService;
import com.app.daily_haul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ProductResponse> addProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, productRequest));
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Void> addProduct(@PathVariable Long productId) {
        boolean deleted = productService.deleteProduct(productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok().body(productService.searchProducts(keyword));
    }

}
