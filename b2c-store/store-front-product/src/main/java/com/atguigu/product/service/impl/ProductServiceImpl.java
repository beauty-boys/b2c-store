package com.atguigu.product.service.impl;

import com.atguigu.clients.CategoryClient;
import com.atguigu.clients.SearchClient;
import com.atguigu.param.*;
import com.atguigu.pojo.Category;
import com.atguigu.pojo.Picture;
import com.atguigu.pojo.Product;
import com.atguigu.product.mapper.PictureMapper;
import com.atguigu.product.mapper.ProductMapper;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private SearchClient searchClient;

    @Cacheable(value ="list.product",key="#categoryName",cacheManager = "cacheManagerDay")
    @Override
    public R promo(String categoryName) {

        R r = categoryClient.byName(categoryName);
        if(r.getCode().equals(R.FAIL_CODE)){
            log.info("ProductServiceImpl.promo业务结束：结果：{}","类别查询失败");
            return r;
        }

        LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) r.getData();

        Integer categoryId = (Integer) map.get("category_id");

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1,7);

        page = productMapper.selectPage(page,queryWrapper);

        List<Product> records = page.getRecords();

        log.info("ProductServiceImpl.promo业务结束：结果：{}",records);
        return R.ok("数据查询成功",records);
    }

    @Cacheable(value ="list.product",key="#productHotParam.categoryName")
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


    @Override
    public R clist() {
        R r = categoryClient.list();
        log.info("ProductServiceImpl.clist业务结束，结果：{}",r);

        return r;

    }

    @Cacheable(value ="list.product",
            key="#productIdsParam.categoryID+'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize")
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {
        List<Integer> categoryID = productIdsParam.getCategoryID();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        if(!categoryID.isEmpty()){
            queryWrapper.in("category_id", categoryID);
        }
        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(),productIdsParam.getPageSize());

        page = productMapper.selectPage(page,queryWrapper);

        R ok = R.ok("查询成功！", page.getRecords(), page.getTotal());
        log.info("ProductServiceImpl.byCategory业务结束。结果：{}",ok);

        return ok;
    }

    @Cacheable(value="product",key="#productId")
    @Override
    public R detail(String productId) {
        Product product = productMapper.selectById(productId);

        R ok = R.ok(product);
        log.info("ProductServiceImpl.detail业务结束，结果：{}",ok);
        return ok;
    }

    @Cacheable(value="pricture",key="#productId")
    @Override
    public R pictures(String productId) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);

        List<Picture> list = pictureMapper.selectList(queryWrapper);

        R ok = R.ok(list);
        log.info("ProcuctServiceImpl.picture业务结束，结果：{}",ok);

        return ok;
    }

    @Cacheable(value="list.category",key="#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public List<Product> allList() {
        List<Product> products = productMapper.selectList(null);
        log.info("ProductServiceImpl.allList业务结束，结果：{}",products.size());
        return products;
    }

    @Override
    public R search(ProductSearchParam productSearchParam) {
        R r = searchClient.search(productSearchParam);
        log.info("ProductServiceImpl.search业务结束，结果：{}",r);
        return r;
    }
}
