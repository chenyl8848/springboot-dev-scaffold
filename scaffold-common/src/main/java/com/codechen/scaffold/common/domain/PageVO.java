package com.codechen.scaffold.common.domain;

import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024-10-22 15:11
 * @description：分页查询
 */
@Data
public abstract class PageVO {

    /** 当前页 */
    private Integer currentPage;

    /** 页码 */
    private Integer pageSize;
}
