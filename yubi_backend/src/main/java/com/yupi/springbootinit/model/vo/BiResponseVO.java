package com.yupi.springbootinit.model.vo;

import lombok.Data;

/**
 * Bi 返回的结果
 */
@Data
public class BiResponseVO {
    private String genChart;

    private String genResult;

    private Long chartId;
}