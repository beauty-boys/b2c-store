package com.atguigu.cart.service;

import com.atguigu.param.CartSaveParam;
import com.atguigu.pojo.Cart;
import com.atguigu.utils.R;

public interface CartService {
    R save(CartSaveParam cartSaveParam);

    R list(Integer userId);

    R update(Cart cart);

    R remove(Cart cart);
}
