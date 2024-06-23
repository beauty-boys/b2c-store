package com.atguigu.user.service;

import com.atguigu.pojo.Address;
import com.atguigu.utils.R;

public interface AddressService {
    R list(Integer userId);

    R save(Address address);

    R remove(Integer id);
}
