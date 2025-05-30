package com.codechen.scaffold.generator.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.generator.domain.entity.FieldInfo;
import com.codechen.scaffold.generator.domain.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-11-6 11:04
 * @description：数据源 Mapper
 */
@Mapper
public interface DataSourceMapper {

    List<TableInfo> queryTableList();

    Page<TableInfo> queryTablePageList(@Param("page") Page<TableInfo> page, @Param("tableName") String tableName);

    TableInfo queryTableInfo(@Param("tableName") String tableName);

    List<FieldInfo> queryFieldList(@Param("tableName") String tableName);

}
