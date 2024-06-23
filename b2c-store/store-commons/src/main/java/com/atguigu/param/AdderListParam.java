package com.atguigu.param;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdderListParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
