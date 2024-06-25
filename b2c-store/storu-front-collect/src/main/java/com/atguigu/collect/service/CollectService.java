package com.atguigu.collect.service;

import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;

public interface CollectService {
    R save(Collect collect);

    R list(Integer userId);


    R move(Collect collect);
}
