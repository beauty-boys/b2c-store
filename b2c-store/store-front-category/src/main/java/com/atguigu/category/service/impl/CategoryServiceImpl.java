package com.atguigu.category.service.impl;

import com.atguigu.category.mapper.CategoryMapper;
import com.atguigu.category.service.CategoryService;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R byName(String categoryName) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", categoryName);
        Category category = categoryMapper.selectOne(queryWrapper);

        if(category==null){
            log.info("CategoryServiceImpl.byName业务结束，结果：{}","类别查询失败");
            return R.fail("类别查询失败！");
        }

        log.info("CategoryServiceImpl.byName业务结束，结果：{}","类别查询成功");
        return R.ok("类别查询成功",category);
    }
}
