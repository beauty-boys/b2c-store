package com.atguigu.user.service;

import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.pojo.User;
import com.atguigu.utils.R;

public interface UserService {

    R check(UserCheckParam userCheckParam);


    R register(User user);

    R login(UserLoginParam userLoginParam);
}
