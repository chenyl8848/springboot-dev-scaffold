package com.codechen.scaffold.generator.service;

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

    public TableInfo tableInfo(String tableName);

    public List<FieldInfo> tableFieldList(String tableName);
}
