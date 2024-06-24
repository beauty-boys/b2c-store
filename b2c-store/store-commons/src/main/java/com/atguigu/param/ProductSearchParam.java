package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductSearchParam extends PageParam{

    private String search;


}
