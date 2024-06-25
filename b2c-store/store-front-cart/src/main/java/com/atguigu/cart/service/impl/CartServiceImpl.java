package com.atguigu.cart.service.impl;

import com.atguigu.cart.mapper.CartMapper;
import com.atguigu.cart.service.CartService;
import com.atguigu.clients.ProductClient;
import com.atguigu.param.CartSaveParam;
import com.atguigu.param.ProductCollectParam;
import com.atguigu.param.ProductIdParam;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import com.atguigu.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (product.getProductNum() == 0) {
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
            cart.setNum(cart.getNum() + 1);
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
        CartVo cartVo = new CartVo(product, cart);
        return R.ok("购物车添加成功！", cartVo);
    }

    @Override
    public R list(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        if (carts != null && carts.size() == 0) {
            carts = new ArrayList<>();
            return R.ok("购物车空空如也", carts);
        }
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : carts) {
            productIds.add(cart.getProductId());
        }
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        List<CartVo> cartVos = new ArrayList<>();
        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVos.add(cartVo);
        }
        R ok = R.ok("数据查询成功！", cartVos);
        log.info("CartServiceImpl.list业务结束，结果：{}", ok);
        return ok;
    }

    @Override
    public R update(Cart cart) {
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdParam);

        if (cart.getNum() > product.getProductNum()) {
            log.info("CartServiceImpl.update业务结束，结果：{}", "修改失败，库存不足！");
            return R.fail("修改失败，库存不足！");
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
        Cart newCart = cartMapper.selectOne(queryWrapper);
        newCart.setNum(cart.getNum());

        int rows = cartMapper.updateById(newCart);
        log.info("CartServiceImpl.update业务结束，结果：{}", rows);
        return R.ok("修改购物车数量成功！");
    }

    @Override
    public R remove(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());

        int rows = cartMapper.delete(queryWrapper);

        log.info("CartServiceImpl.remove业务结束，结果：{}", rows);
        return R.ok("删除购物车数据成功！");
    }

}
