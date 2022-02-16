package com.example.httpout.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/15
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
}