package com.atguigu.collect.service.impl;

import com.atguigu.collect.mapper.CollectMapper;
import com.atguigu.collect.service.CollectService;
import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollerServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public R save(Collect collect) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", collect.getUserId());
        queryWrapper.eq("product_id", collect.getProductId());

        Long count = collectMapper.selectCount(queryWrapper);

        if(count>0){
            return R.fail("收藏已经添加，无需二次添加");
        }

        collect.setCollectTime(System.currentTimeMillis());
        int insert = collectMapper.insert(collect);
        log.info("CollectServiceImpl.save业务结束，结果：{}",insert);

        return R.ok("收藏添加成功");
    }
}
