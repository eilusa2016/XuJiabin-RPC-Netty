package com.xjb.rpc.api.pojo;


import lombok.*;

import java.io.Serializable;

/**
 * @program: xjb-rpc
 * @description: 测试的pojo
 * @author: karl.xu
 * @create: 2020-10-14 17:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class Hello implements Serializable {
    private String message;
    private String description;
}
