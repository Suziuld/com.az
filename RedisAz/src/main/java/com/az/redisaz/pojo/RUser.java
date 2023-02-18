package com.az.redisaz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author:fs
 * @Date:2023/2/1322:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RUser implements Serializable {
    private Integer id;
    private String username;
    private String pwd;
}
