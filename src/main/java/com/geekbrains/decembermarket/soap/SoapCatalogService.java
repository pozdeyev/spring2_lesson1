package com.geekbrains.decembermarket.soap;
import com.geekbrains.decembermarket.beans.Cart;
import com.geekbrains.decembermarket.entites.ProductDto;

import com.geekbrains.decembermarket.repositories.ProductRepository;
import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.soap.catalog.ProductSoapDto;
import com.geekbrains.decembermarket.soap.catalog.ProductsList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SoapCatalogService {
    private ProductRepository productRepository;
    private List<ProductSoapDto> productSoapDtos;


    public SoapCatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        productSoapDtos = new ArrayList<>();
        List<ProductDto> productDtos = productRepository.findAllBy();
        Iterator productDto_iterator = productDtos.iterator();

        while (productDto_iterator.hasNext()) {
            ProductDto element = (ProductDto) productDto_iterator.next();

            //создаем DTO SOAP
            ProductSoapDto productSoapDto = new ProductSoapDto();
            productSoapDto.setTitle(element.getTitle());
            productSoapDto.setPrice(element.getPrice());
            productSoapDtos.add(productSoapDto);
        }
    }

    public ProductsList getAllProductsList() {
        ProductsList productsList = new ProductsList();
        productsList.getProduct().addAll(productSoapDtos);
        return productsList;
    }
}
