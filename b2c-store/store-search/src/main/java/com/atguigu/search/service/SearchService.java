package com.atguigu.search.service;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchService {
    R search(ProductSearchParam productSearchParam) ;
}
