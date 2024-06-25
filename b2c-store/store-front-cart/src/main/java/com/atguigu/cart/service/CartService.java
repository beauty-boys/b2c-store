package com.atguigu.cart.service;

import com.atguigu.param.CartSaveParam;
import com.atguigu.utils.R;

public interface CartService {
    R save(CartSaveParam cartSaveParam);
}
