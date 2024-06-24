package com.atguigu.product.service.impl;

import com.atguigu.clients.CategoryClient;
import com.atguigu.param.ProductHotParam;
import com.atguigu.param.ProductPromoParam;
import com.atguigu.pojo.Category;
import com.atguigu.pojo.Product;
import com.atguigu.product.mapper.ProductMapper;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public R promo(String categoryName) {

        R r = categoryClient.byName(categoryName);
        if(r.getCode().equals(R.FAIL_CODE)){
            log.info("ProductServiceImpl.promo业务结束：结果：{}","类别查询失败");
            return r;
        }

        LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) r.getData();

        Integer categoryId = (Integer) map.get("categoryId");

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1,7);

        page = productMapper.selectPage(page,queryWrapper);

        List<Product> records = page.getRecords();

        log.info("ProductServiceImpl.promo业务结束：结果：{}",records);
        return R.ok("数据查询成功",records);
    }

    @Override
    public R hots(ProductHotParam productHotParam) {

        R r = categoryClient.hots(productHotParam);
        if(r.getCode().equals(R.FAIL_CODE)){
            log.info("ProductHotParamImpl.hots业务结束，结果:{}",r.getMsg());
            return r;
        }
         List<Object> ids = (List<Object>) r.getData();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id", ids);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page,queryWrapper);

        List<Product> records = page.getRecords();

        R ok = R.ok("多累别热门商品查询成功！", records);
        log.info("ProductHotParamImpl.hots业务结束，结果:{}",ok);

        return ok;

    }
}
