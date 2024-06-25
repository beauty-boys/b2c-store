package com.atguigu.cart.service.impl;

import com.atguigu.cart.mapper.CartMapper;
import com.atguigu.cart.service.CartService;
import com.atguigu.clients.ProductClient;
import com.atguigu.param.CartSaveParam;
import com.atguigu.param.ProductIdParam;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import com.atguigu.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public R save(CartSaveParam cartSaveParam) {
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);

        if (product == null) {
            return R.fail("商品已经被删除了，无法添加到购物车！");
        }

        if(product.getProductNum()==0){
            R ok = R.ok("没有库存数据！无法购买");
            ok.setCode("003");
            log.info("CartServiceImpl.save业务结束，结果:{}", ok);
            return ok;
        }

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartSaveParam.getUserId());
        queryWrapper.eq("product_id", cartSaveParam.getProductId());

        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart != null) {
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);
            R ok = R.ok("购物车存在该商品，数量+1");
            ok.setCode("002");
            log.info("CartServiceImpl.save业务结束，结果:{}", ok);
            return ok;
        }
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        int rows = cartMapper.insert(cart);
        log.info("CartServiceImpl.save业务结束，结果:{}", rows);
        CartVo cartVo = new CartVo(product,cart);
        return R.ok("购物车添加成功！",cartVo);
    }
}
