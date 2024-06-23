package com.atguigu.user.controller;


import com.atguigu.param.AdderListParam;
import com.atguigu.param.AddressRemoveParam;
import com.atguigu.pojo.Address;
import com.atguigu.user.service.AddressService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/address")
public class addressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("list")
    public R list(@RequestBody @Validated AdderListParam adderListParam,
                  BindingResult result) {
        if(result.hasErrors()) {
            return R.fail("参数异常,查询失败");
        }
        return addressService.list(adderListParam.getUserId());
    }

    @PostMapping("save")
    public R save(@RequestBody @Validated Address address,
                  BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,保存失败");
        }

        return addressService.save(address);
    }

    @PostMapping("remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam,
                    BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,保存失败");
        }
        return addressService.remove(addressRemoveParam.getId());
    }


}
