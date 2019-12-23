package com.geekbrains.decembermarket.controllers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class RestApiController {
    private ProductService productService;

    @Autowired
    public void setItemService(ProductService itemService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonBackReference
    public List<Product> getAllItems() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveNewProduct(@RequestBody Product item) {
        return productService.save(item);
    }

    @DeleteMapping("/del/{id}")
    public void delProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.save(product);
    }

}
