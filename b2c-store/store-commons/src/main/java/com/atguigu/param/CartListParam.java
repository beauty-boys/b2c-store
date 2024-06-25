package com.atguigu.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartListParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;

//    @JsonProperty("product_id")
//    private Integer productId;

//    private Integer num;
}
