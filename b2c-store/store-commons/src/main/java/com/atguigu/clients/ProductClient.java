package com.atguigu.clients;

import com.atguigu.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "product-service")
public interface ProductClient {

    @GetMapping("/product/list")
    List<Product> allList();
}
