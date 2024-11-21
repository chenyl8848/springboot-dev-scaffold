package com.codechen.scaffold.generator.service;

import com.codechen.scaffold.generator.domain.vo.PreviewCodeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-11-1 17:29
 * @description：代码生成器 Service
 */
public interface IGeneratorService {

    /**
     * 代码预览
     *
     * @param tableName 表名
     * @return
     */
    public List<PreviewCodeVo> generateCodePreview(String tableName);

    /**
     * 生成本地代码文件
     *
     * @param tableName 表名
     * @param filePath  文件路径
     */
    public void generateCodeFile(String tableName, String filePath);

    /**
     * 生成 Zip 包代码
     *
     * @param tableName
     * @param response
     */
    public void generateCodeZip(String tableName, HttpServletResponse response) throws IOException;

    /**
     * 批量生成本地代码文件
     *
     * @param tableNames 表名集合
     * @param filePath   文件路径
     */
    public void batchGenerateCodeFile(List<String> tableNames, String filePath);

    /**
     * 批量生成 Zip 包代码
     *
     * @param tableNames
     * @param response
     */
    public void batchGenerateCodeZip(List<String> tableNames, HttpServletResponse response) throws IOException;

}
