package com.gopiwebdev.ecommerce.cart_service.client;

import com.gopiwebdev.ecommerce.cart_service.config.FeignClientConfig;
import com.gopiwebdev.ecommerce.cart_service.entity.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductClient {

    @GetMapping("api/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
