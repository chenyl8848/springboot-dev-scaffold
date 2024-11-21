package com.codechen.scaffold.generator.controller;

import com.codechen.scaffold.generator.domain.entity.TableInfo;
import com.codechen.scaffold.generator.domain.vo.PreviewCodeVo;
import com.codechen.scaffold.generator.service.IDataSourceService;
import com.codechen.scaffold.generator.service.IGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date：2024-11-5 16:00
 * @description：页面 Controller
 */
@Controller
@Slf4j
public class PageController {

    @Autowired
    private IDataSourceService dataSourceService;

    @Autowired
    private IGeneratorService generatorService;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<TableInfo> tableInfoList = dataSourceService.tableInfoList();

        model.addAttribute("table", tableInfoList);
        return "index";
    }

    @GetMapping("/generateCodeString/{tableName}")
    @ResponseBody
    public List<PreviewCodeVo> generateCodeString(@PathVariable String tableName) {
        return generatorService.generateCodePreview(tableName);
    }

    @GetMapping("generateCodeFile")
    @ResponseBody
    public String generatorCodeFile(@RequestParam String tableName,
                                    @RequestParam String filePath) {

        generatorService.generateCodeFile(tableName, filePath);

        return "success";
    }

    @GetMapping("/generateCodeZip")
    public void generateCodeZip(@RequestParam String tableName,
                                HttpServletResponse response) throws IOException {
        String fileName = "code.zip";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        generatorService.generateCodeZip(tableName, response);
    }

    @GetMapping("batchGenerateCodeFile")
    @ResponseBody
    public String batchGenerateCodeFile(@RequestParam(value = "tableNames[]") List<String> tableNames,
                                        @RequestParam(value = "filePath") String filePath) {

        generatorService.batchGenerateCodeFile(tableNames, filePath);

        return "success";
    }

    @GetMapping("/batchGenerateCodeZip")
    public void batchGenerateCodeZip(@RequestParam List<String> tableNames,
                                     HttpServletResponse response) throws IOException {
        String fileName = "code.zip";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        generatorService.batchGenerateCodeZip(tableNames, response);
    }
}
