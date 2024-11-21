package com.codechen.scaffold.generator.service.impl;

import com.codechen.scaffold.generator.config.GeneratorConfig;
import com.codechen.scaffold.generator.constant.GeneratorConstants;
import com.codechen.scaffold.generator.domain.entity.FieldInfo;
import com.codechen.scaffold.generator.domain.entity.TableInfo;
import com.codechen.scaffold.generator.domain.vo.PreviewCodeVo;
import com.codechen.scaffold.generator.service.IDataSourceService;
import com.codechen.scaffold.generator.service.IGeneratorService;
import com.codechen.scaffold.generator.util.GeneratorUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author：Java陈序员
 * @date：2024-11-1 17:30
 * @description：代码生成器 Service Impl
 */
@Service
@Slf4j
public class GeneratorServiceImpl implements IGeneratorService {

    private List<String> templates = new ArrayList<>();

    @PostConstruct
    private void initTemplates() {
        log.info("templates:{}", templates);
        templates.add("java/entity.ftl");
        templates.add("java/mapper.ftl");
        templates.add("java/service.ftl");
        templates.add("java/serviceImpl.ftl");
        templates.add("java/controller.ftl");
        templates.add("mapper/mapper.ftl");
        log.info("templates:{}", templates);
    }

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private GeneratorConfig generatorConfig;

    @Autowired
    private IDataSourceService dataSourceService;

    @Override
    public List<PreviewCodeVo> generateCodePreview(String tableName) {
        log.info("tableName:{}", tableName);

        // 数据
        Map<String, Object> model = prepareContext(tableName);

        // 模板
        List<Template> templateList = getTemplateList();

        List<PreviewCodeVo> list = templateList.stream().map(template -> {
            StringWriter stringWriter = new StringWriter();
            generatorCode(template, model, stringWriter);

            log.info("\n{} \n{}", template.getName(), stringWriter);

            PreviewCodeVo previewCodeVo = new PreviewCodeVo();
            previewCodeVo.setTemplateName(template.getName());
            previewCodeVo.setCode(stringWriter.toString());

            return previewCodeVo;
        }).collect(Collectors.toList());

        return list;
    }

    @Override
    public void generateCodeFile(String tableName, String filePath) {
        log.info("tableName: {} filePath: {}", tableName, filePath);

        // 模板
        List<Template> templateList = getTemplateList();

        // 数据
        Map<String, Object> model = prepareContext(tableName);

        for (Template template : templateList) {
            String fileName = getFileName(GeneratorUtil.convertClassName(tableName,
                    generatorConfig.getIgnoreTablePrefix(),
                    generatorConfig.getTablePrefix()), template.getName());
            String path = filePath.concat(fileName);

            log.info("fileName: {} path: {}", fileName, path);
            File file = new File(path);

            File parentFile = file.getParentFile();
            if (parentFile == null || !parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            generatorCode(template, model, fileWriter);
        }

    }

    @Override
    public void generateCodeZip(String tableName, HttpServletResponse response) throws IOException {
        log.info("tableName: {}", tableName);

        // 模板
        List<Template> templateList = getTemplateList();

        // 数据
        Map<String, Object> model = prepareContext(tableName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        templateList.forEach(template -> {

            StringWriter writer = new StringWriter();
            try {
                zipOutputStream.putNextEntry(new ZipEntry(getFileName(GeneratorUtil.convertClassName(tableName,
                        generatorConfig.getIgnoreTablePrefix(),
                        generatorConfig.getTablePrefix()), template.getName())));
                generatorCode(template, model, writer);
                IOUtils.write(writer.toString(), zipOutputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        IOUtils.closeQuietly(zipOutputStream);

        response.getOutputStream().write(outputStream.toByteArray());
    }

    @Override
    public void batchGenerateCodeFile(List<String> tableNames, String filePath) {
        tableNames.forEach(tableName -> {
            generateCodeFile(tableName, filePath);
        });
    }

    @Override
    public void batchGenerateCodeZip(List<String> tableNames, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        tableNames.forEach(tableName -> {
            log.info("tableName: {}", tableName);

            // 模板
            List<Template> templateList = getTemplateList();

            // 数据
            Map<String, Object> model = prepareContext(tableName);

            templateList.forEach(template -> {

                StringWriter writer = new StringWriter();
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(getFileName(GeneratorUtil.convertClassName(tableName,
                            generatorConfig.getIgnoreTablePrefix(),
                            generatorConfig.getTablePrefix()), template.getName())));
                    generatorCode(template, model, writer);
                    IOUtils.write(writer.toString(), zipOutputStream, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        });

        IOUtils.closeQuietly(zipOutputStream);
        response.getOutputStream().write(outputStream.toByteArray());
    }

    /**
     * 获取代码文件名
     *
     * @param className    类名
     * @param templateName 模板
     * @return
     */
    private String getFileName(String className, String templateName) {

        StringBuilder stringBuilder = new StringBuilder("main/");

        if (templateName.contains("mapper/mapper")) {

            stringBuilder.append("resources/mapper/");
            stringBuilder.append(className);
            stringBuilder.append(generatorConfig.getMapperSuffix());
            stringBuilder.append(".xml");

            return stringBuilder.toString();
        }

        stringBuilder.append("java/");
        stringBuilder.append(generatorConfig.getPackageName().replace(".", "/"));


        if (templateName.contains("java/entity")) {

            stringBuilder.append("/entity/");
            stringBuilder.append(className);
            stringBuilder.append(generatorConfig.getEntitySuffix());
            stringBuilder.append(".java");

            return stringBuilder.toString();
        }

        if (templateName.contains("java/mapper")) {

            stringBuilder.append("/");
            stringBuilder.append(generatorConfig.getMapperSuffix().toLowerCase());
            stringBuilder.append("/");
            stringBuilder.append(className);
            stringBuilder.append(generatorConfig.getMapperSuffix());
            stringBuilder.append(".java");

            return stringBuilder.toString();
        }

        if (templateName.contains("java/serviceImpl")) {

            stringBuilder.append("/service/impl/");
            stringBuilder.append(className);
            stringBuilder.append("ServiceImpl");
            stringBuilder.append(".java");

            return stringBuilder.toString();
        }

        if (templateName.contains("java/service")) {

            stringBuilder.append("/service/");
            stringBuilder.append("I");
            stringBuilder.append(className);
            stringBuilder.append("Service");
            stringBuilder.append(".java");

            return stringBuilder.toString();
        }

        if (templateName.contains("java/controller")) {

            stringBuilder.append("/controller/");
            stringBuilder.append(className);
            stringBuilder.append("Controller");
            stringBuilder.append(".java");

            return stringBuilder.toString();
        }

        return null;
    }

    /**
     * 数据准备
     *
     * @param tableName 表名
     * @return
     */
    private Map<String, Object> prepareContext(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            throw new IllegalArgumentException("表名不能为空！");
        }

        // 表信息
        TableInfo tableInfo = dataSourceService.tableInfo(tableName);

        // 字段信息
        List<FieldInfo> fieldInfoList = dataSourceService.tableFieldList(tableName);
        // 导入包信息
        Set<String> importList = getImportList(fieldInfoList);

        // 模板集合
        List<Template> templateList = getTemplateList();
        if (templateList == null || templateList.isEmpty()) {
            throw new IllegalArgumentException("模板不存在！");
        }

        Map<String, Object> model = new HashMap<>();

        model.put("packageName", generatorConfig.getPackageName());
        model.put("author", generatorConfig.getAuthor());
        if (StringUtils.isNotBlank(generatorConfig.getCreateTime())) {
            model.put("date", generatorConfig.getCreateTime());
        } else {
            model.put("date", GeneratorUtil.convertCreateTime(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
        }

        model.put("tableName", GeneratorUtil.convertClassName(tableName,
                generatorConfig.getIgnoreTablePrefix(),
                generatorConfig.getTablePrefix()));

        if (StringUtils.isNotBlank(tableInfo.getTableComment())) {
            model.put("tableComment", tableInfo.getTableComment());
        } else {
            model.put("tableComment", tableInfo.getClassName());
        }

        model.put("entitySuffix", generatorConfig.getEntitySuffix());
        model.put("mapperSuffix", generatorConfig.getMapperSuffix());
        model.put("filedInfoList", fieldInfoList);
        model.put("importList", importList);

        model.put("enableSwagger", generatorConfig.getEnableSwagger());

        String baseEntity = generatorConfig.getBaseEntity();
        if (StringUtils.isNotBlank(baseEntity)) {
            // 配置 entity 基类
            model.put("baseEntityPackage", baseEntity);
            model.put("baseEntity", baseEntity.substring(baseEntity.lastIndexOf(".") + 1));
        }

        String commonResult = generatorConfig.getCommonResult();
        if (StringUtils.isNotBlank(commonResult)) {
            // 配置 controller 统一返回实体
            model.put("commonResultPackage", commonResult);
            model.put("commonResult", commonResult.substring(commonResult.lastIndexOf(".") + 1));
        }

        return model;
    }

    /**
     * 生成代码
     *
     * @param template 模板
     * @param model    数据
     * @param writer   输出流
     */
    private void generatorCode(Template template, Map<String, Object> model, Writer writer) {
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取模板列表
     *
     * @return
     */
    private List<Template> getTemplateList() {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        List<Template> templateList = templates.stream()
                .map(item -> {
                    Template template = null;
                    try {
                        template = configuration.getTemplate(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return template;
                }).collect(Collectors.toList());

        if (templateList == null || templateList.isEmpty()) {
            throw new IllegalArgumentException("模板不存在！");
        }

        return templateList;
    }

    /**
     * 获取导入列表
     *
     * @param fieldInfoList
     * @return
     */
    private Set<String> getImportList(List<FieldInfo> fieldInfoList) {
        Set<String> importList = new HashSet<>();

        fieldInfoList.forEach(item -> {

            if (GeneratorConstants.FIELD_TYPE_DATE.equals(item.getFieldType())) {
                importList.add("import java.util.Date;");
            }

            if (GeneratorConstants.FIELD_TYPE_BIGDECIMAL.equals(item.getFieldType())) {
                importList.add("import java.math.BigDecimal;");
            }
        });
        return importList;
    }
}
