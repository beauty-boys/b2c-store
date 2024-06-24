package com.atguigu.product.service;

import com.atguigu.param.*;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;

import java.util.List;

public interface ProductService {
    R promo(String categoryName);

    R hots(ProductHotParam productHotParam);

    R clist();

    R byCategory(ProductIdsParam productIdsParam);

    R detail(String productId);

    R pictures(String productId);

    List<Product> allList();

    R search(ProductSearchParam productSearchParam);
}
