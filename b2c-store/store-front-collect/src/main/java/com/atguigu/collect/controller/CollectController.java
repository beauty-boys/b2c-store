package com.atguigu.collect.controller;

import com.atguigu.collect.service.CollectService;
import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collect")
public class CollectController {


    @Autowired
    private CollectService collectService;

    @PostMapping("save")
    public R save(@RequestBody Collect collect) {
        return collectService.save(collect);
    }

    @PostMapping("list")
    public R list(@RequestBody Collect collect) {
        return collectService.list(collect.getUserId());
    }
    @PostMapping("remove")
    public R move(@RequestBody Collect collect) {
        return collectService.move(collect);
    }

}
