package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("product_picture")
public class Picture {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("product_id")
    @JsonProperty("product_id")
    private Integer productId;

    @TableField("product_picture")
    @JsonProperty("product_picture")
    private String productPicture;

    private String intro;
}
