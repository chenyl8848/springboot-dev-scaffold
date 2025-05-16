package com.codechen.scaffold.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.common.exception.GlobalException;
import com.codechen.scaffold.generator.config.GeneratorConfig;
import com.codechen.scaffold.generator.domain.entity.FieldInfo;
import com.codechen.scaffold.generator.domain.entity.TableInfo;
import com.codechen.scaffold.generator.mapper.DataSourceMapper;
import com.codechen.scaffold.generator.service.IDataSourceService;
import com.codechen.scaffold.generator.util.GeneratorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date：2024-11-6 10:16
 * @description：数据源 ServiceImpl
 */
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private GeneratorConfig generatorConfig;

    @Override
    public List<TableInfo> tableInfoList() {
        List<TableInfo> list = dataSourceMapper.queryTableList();

        list.forEach(item -> item.setClassName(GeneratorUtil.convertClassName(item.getTableName(),
                generatorConfig.getIgnoreTablePrefix(),
                generatorConfig.getTablePrefix())));
        return list;
    }

    @Override
    public IPage<TableInfo> tableInfoPageList(Page<TableInfo> tableInfoPage, String tableName) {
        Page<TableInfo> page = dataSourceMapper.queryTablePageList(tableInfoPage, tableName);

        page.getRecords().forEach(item -> {
            item.setClassName(GeneratorUtil.convertClassName(item.getTableName(),
                    generatorConfig.getIgnoreTablePrefix(),
                    generatorConfig.getTablePrefix()));
        });
        return page;
    }

    @Override
    public TableInfo tableInfo(String tableName) {
        TableInfo tableInfo = dataSourceMapper.queryTableInfo(tableName);

        if (tableInfo == null) {
            throw new IllegalArgumentException("表【" + tableName + "】不存在");
        }
        tableInfo.setClassName(GeneratorUtil.convertClassName(tableInfo.getTableName(),
                generatorConfig.getIgnoreTablePrefix(),
                generatorConfig.getTablePrefix()));
        return tableInfo;
    }

    @Override
    public List<FieldInfo> tableFieldList(String tableName) {
        List<FieldInfo> list = dataSourceMapper.queryFieldList(tableName);

        if (StringUtils.isNotBlank(generatorConfig.getIgnoreColumn())) {
            List<String> strings = Arrays.asList(generatorConfig.getIgnoreColumn().split(","));
            list = list.stream()
                    .filter(item -> {
                        return !strings.contains(item.getColumnName().toLowerCase());
                    })
                    .collect(Collectors.toList());
        }

        list.forEach(item -> {
                    item.setFileName(GeneratorUtil.convertFieldName(item.getColumnName()));
                    item.setFieldType(GeneratorUtil.convertFieldType(item.getDataType(), item.getColumnType()));
                }
        );

        return list;
    }
}
