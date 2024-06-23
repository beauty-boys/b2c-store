package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("carousel")
@Data
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("carousel_id")
    @TableId(value="carousel_id",type= IdType.AUTO)
    private Integer carouselId;

    @TableField("img_path")
    private String imgPath;

    private String describes;

    @JsonProperty("product_id")
    @TableField("product_id")
    private Integer productId;

    private Integer priority;
}
