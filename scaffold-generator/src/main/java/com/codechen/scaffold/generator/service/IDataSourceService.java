package com.codechen.scaffold.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.generator.domain.entity.FieldInfo;
import com.codechen.scaffold.generator.domain.entity.TableInfo;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-11-6 10:15
 * @description：数据源 service
 */
public interface IDataSourceService {

    public List<TableInfo> tableInfoList();

    IPage<TableInfo> tableInfoPageList(Page<TableInfo> tableInfoPage, String tableName);

    public TableInfo tableInfo(String tableName);

    public List<FieldInfo> tableFieldList(String tableName);

}
