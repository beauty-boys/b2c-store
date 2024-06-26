package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_id")
    @TableId(type= IdType.AUTO)
    private Integer userId;

    @Length(min=6)
    private String userName;

//    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @NotBlank
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @NotBlank
    private String userPhonenumber;

}
