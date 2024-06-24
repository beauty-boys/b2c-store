package com.atguigu.search.controller;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.search.service.SearchService;
import com.atguigu.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class SerachController {

    @Autowired
    private SearchService searchService;

    @PostMapping("product")
    public R search(@RequestBody ProductSearchParam productSearchParam) {
        return searchService.search(productSearchParam);
    }
}
