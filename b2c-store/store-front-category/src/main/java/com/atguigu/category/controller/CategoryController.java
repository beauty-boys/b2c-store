package com.atguigu.category.controller;

import com.atguigu.category.service.CategoryService;
import com.atguigu.param.ProductHotParam;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName) {

        if(StringUtils.isEmpty(categoryName)){
            return R.fail("类别名为null,无法查询类别数据！");
        }

        return categoryService.byName(categoryName);
    }

    @PostMapping("hots")
    public R hotsCategory(@RequestBody @Valid ProductHotParam productHotParam,
                          BindingResult result) {
        if(result.hasErrors()){
            return R.fail("类别集合查询失败");
        }
        return categoryService.hotsCategory(productHotParam);

    }
}
