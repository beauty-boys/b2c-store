package com.atguigu.user.service.impl;

import com.atguigu.constants.UserConstants;
import com.atguigu.param.UserCheckParam;
import com.atguigu.pojo.User;
import com.atguigu.user.mapper.UserMapper;
import com.atguigu.user.service.UserService;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userCheckParam.getUserName());

        Long total = userMapper.selectCount(queryWrapper);

        if(total==0){
            log.info("UserServiceImpl.check业务结束，结果{}","账号可以使用！");
            return  R.ok("账号不存在，可以使用");

        }

        log.info("UserServiceImpl.check业务结束，结果{}","账号不可以使用！");
        return R.fail("账号已经存在，不可注册");
    }


    @Override
    public R register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());

        Long total = userMapper.selectCount(queryWrapper);

        if(total>0){
            log.info("UserServiceIml.register业务结束，结果:{}","账号存在，注册失败");
            return R.fail("账号已经存在，不可注册");
        }
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);

        int rows = userMapper.insert(user);

        if(rows==0){
            log.info("UserServiceImpl.register业务结束，结果；{}","数据插入失败！注册失败！");
            return R.fail("注册失败！请稍后重试！");
        }

        log.info("UserServiceImpl.register业务结束，结果；{}","数据插入成功！注册成功！");
        return R.ok("注册成功");
    }


}
