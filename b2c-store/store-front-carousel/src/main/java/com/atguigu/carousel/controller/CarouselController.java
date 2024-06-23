package com.atguigu.carousel.controller;

import com.atguigu.carousel.service.CarouselServive;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carousel")
public class CarouselController {

    @Autowired
    private CarouselServive carouselServive;


    @PostMapping("list")
    public R list(){

        return carouselServive.list();
    }
}
