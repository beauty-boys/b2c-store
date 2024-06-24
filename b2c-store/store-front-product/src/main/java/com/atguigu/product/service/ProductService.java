package com.atguigu.product.service;

import com.atguigu.param.ProductHotParam;
import com.atguigu.param.ProductIdParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.param.ProductPromoParam;
import com.atguigu.utils.R;

public interface ProductService {
    R promo(String categoryName);


    R hots(ProductHotParam productHotParam);

    R clist();

    R byCategory(ProductIdsParam productIdsParam);

    R detail(String productId);

    R pictures(String productId);
}
