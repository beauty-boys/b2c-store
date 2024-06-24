package com.atguigu.category.service;

import com.atguigu.param.ProductHotParam;
import com.atguigu.utils.R;

public interface CategoryService {
    R byName(String categoryName);

    R hotsCategory(ProductHotParam productHotParam);
}
