package com.atguigu.product.controller;

import com.atguigu.param.ProductPromoParam;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam productPromoParam,
                   BindingResult result) {
        if(result.hasErrors()){
            return R.fail("数据查询失败！");
        }
        return productService.promo(productPromoParam.getCategoryName());
    }

}
