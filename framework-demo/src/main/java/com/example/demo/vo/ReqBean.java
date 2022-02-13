package com.example.demo.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
@Data
@Builder
public class ReqBean {
   @NotNull
   @Min(3) @Max(10)
    private int age;
   @Size(min=2,max = 10)
    private String name;

}