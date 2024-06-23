package com.atguigu.category.controller;

import com.atguigu.category.service.CategoryService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
}
