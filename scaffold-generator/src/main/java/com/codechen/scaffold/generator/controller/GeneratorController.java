package com.codechen.scaffold.generator.controller;

import com.codechen.scaffold.common.domain.Result;
import com.codechen.scaffold.common.enums.ResultCodeEnum;
import com.codechen.scaffold.generator.domain.entity.FieldInfo;
import com.codechen.scaffold.generator.domain.entity.TableInfo;
import com.codechen.scaffold.generator.domain.request.BatchGenerateCodeRequest;
import com.codechen.scaffold.generator.domain.request.GenerateCodeRequest;
import com.codechen.scaffold.generator.domain.vo.PreviewCodeVo;
import com.codechen.scaffold.generator.service.IDataSourceService;
import com.codechen.scaffold.generator.service.IGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-11-20 17:28
 * @description：
 */
@RestController
@Api(tags = "代码生成器")
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private IDataSourceService dataSourceService;

    @Autowired
    private IGeneratorService generatorService;

    @ApiOperation(value = "数据库表信息")
    @GetMapping("/tableInfoList")
    public Result<List<TableInfo>> tableInfoList() {
        List<TableInfo> tableInfoList = dataSourceService.tableInfoList();

        return Result.success(tableInfoList);
    }

    @ApiOperation(value = "数据表信息")
    @GetMapping("/tableInfo/{tableName}")
    public Result<TableInfo> tableInfo(@PathVariable("tableName") String tableName) {
        TableInfo tableInfo = dataSourceService.tableInfo(tableName);

        return Result.success(tableInfo);
    }

    @ApiOperation(value = "数据库表字段信息")
    @GetMapping("/tableFieldList/{tableName}")
    public Result<List<FieldInfo>> tableFieldList(@PathVariable("tableName") String tableName) {
        List<FieldInfo> tableFieldList = dataSourceService.tableFieldList(tableName);

        return Result.success(tableFieldList);
    }

    @ApiOperation(value = "生成代码预览")
    @GetMapping("/generateCodeString/{tableName}")
    public Result<List<PreviewCodeVo>> generateCodeString(@PathVariable("tableName") String tableName) {
        return Result.success(generatorService.generateCodePreview(tableName));
    }

    @ApiOperation(value = "生成本地代码文件")
    @PostMapping("generateCodeFile")
    public Result<String> generatorCodeFile(@RequestBody GenerateCodeRequest generateCodeRequest) {

        generatorService.generateCodeFile(generateCodeRequest.getTableName(), generateCodeRequest.getFilePath());

        return Result.success("success");
    }

    @ApiOperation(value = "生成 Zip 包代码文件")
    @GetMapping("/generateCodeZip/{tableName}")
    public void generateCodeZip(@PathVariable("tableName") String tableName,
                                HttpServletResponse response) throws IOException {
        String fileName = "code.zip";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        generatorService.generateCodeZip(tableName, response);
    }

    @ApiOperation(value = "批量生成本地代码文件")
    @PostMapping("batchGenerateCodeFile")
    public Result batchGenerateCodeFile(@RequestBody BatchGenerateCodeRequest request) {
        if (CollectionUtils.isEmpty(request.getTableNames())) {
            return Result.fail(ResultCodeEnum.PARAM_ABSENT.getCode(), "表信息不能为空");
        }

        if (StringUtils.isBlank(request.getFilePath())) {
            return Result.fail(ResultCodeEnum.PARAM_ABSENT.getCode(), "文件路径不能为空");
        }

        generatorService.batchGenerateCodeFile(request.getTableNames(), request.getFilePath());

        return Result.success("success");
    }

    @ApiOperation(value = "批量生成 Zip 包代码文件")
    @PostMapping("/batchGenerateCodeZip")
    public void batchGenerateCodeZip(@RequestBody BatchGenerateCodeRequest request,
                                     HttpServletResponse response) throws IOException {
        if (CollectionUtils.isEmpty(request.getTableNames())) {
            throw new IllegalArgumentException("表信息不能为空");
        }

        String fileName = "code.zip";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        generatorService.batchGenerateCodeZip(request.getTableNames(), response);
    }
}
