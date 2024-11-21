package com.codechen.scaffold.generator.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author：Java陈序员
 * @date：2024-11-1 17:27
 * @description：代码生成器 Controller
 */
@RestController
@Slf4j
@RequestMapping("/test")
@Deprecated
public class GeneratorTestController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @GetMapping("/generateCodeString")
    public String generateCodeString(Model model) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("java/mapper.ftl");
        StringWriter writer = new StringWriter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("author", "Java 陈序员");
        model.addAttribute("tableName", "Document");
        model.addAttribute("tableComment", "文档");
        model.addAttribute("date", dateTimeFormatter.format(LocalDateTime.now()));
        model.addAttribute("packageName", "com.codechen.library.api");

        template.process(model, writer);
        return writer.toString();
    }

    @GetMapping("/generateCodeFile")
    public String generateCodeFile(Model model) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("java/mapper.ftl");

        File file = new File("E:\\JavaEE\\backend\\chen-library\\chen-library-serve-generator\\src\\main\\java\\com\\codechen\\library\\generator\\mapper\\DocumentMapper.java");
        File parentFile = file.getParentFile();
        if (parentFile == null || !parentFile.exists()) {
            parentFile.mkdir();
        }

        FileWriter writer = new FileWriter(file, true);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("author", "Java 陈序员");
        model.addAttribute("tableName", "Document");
        model.addAttribute("tableComment", "文档");
        model.addAttribute("date", dateTimeFormatter.format(LocalDateTime.now()));
        model.addAttribute("packageName", "com.codechen.library.api");

        template.process(model, writer);

        return "成功";
    }

    @GetMapping("/generateCodePreview")
    public void generateCodePreview(Model model, HttpServletResponse response) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("java/mapper.ftl");

        response.setContentType("application/json;charset=utf-8");
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("author", "Java 陈序员");
        model.addAttribute("tableName", "Document");
        model.addAttribute("tableComment", "文档");
        model.addAttribute("date", dateTimeFormatter.format(LocalDateTime.now()));
        model.addAttribute("packageName", "com.codechen.library.api");

        template.process(model, writer);
    }

    @GetMapping("/generateCodeDownload")
    public void generateCodeDownload(Model model, HttpServletResponse response) throws IOException, TemplateException {
        String fileName = "DocumentMapper.java";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("java/mapper.ftl");

        response.setContentType("application/json;charset=utf-8");
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("author", "Java 陈序员");
        model.addAttribute("tableName", "Document");
        model.addAttribute("tableComment", "文档");
        model.addAttribute("date", dateTimeFormatter.format(LocalDateTime.now()));
        model.addAttribute("packageName", "com.codechen.library.api");

        template.process(model, writer);
    }

    @GetMapping("/generateCodeZip")
    public void generateCodeZip(Model model, HttpServletResponse response) throws IOException, TemplateException {
        String fileName = "code.zip";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("java/mapper.ftl");
        StringWriter writer = new StringWriter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("author", "Java 陈序员");
        model.addAttribute("tableName", "Document");
        model.addAttribute("tableComment", "文档");
        model.addAttribute("date", dateTimeFormatter.format(LocalDateTime.now()));
        model.addAttribute("packageName", "com.codechen.library.api");

        template.process(model, writer);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        zipOutputStream.putNextEntry(new ZipEntry("mapper/DocumentMapper.java"));
        IOUtils.write(writer.toString(), zipOutputStream, StandardCharsets.UTF_8);

        IOUtils.closeQuietly(zipOutputStream);

//        IOUtils.write(outputStream.toByteArray(), response.getOutputStream());
        response.getOutputStream().write(outputStream.toByteArray());
    }

}
