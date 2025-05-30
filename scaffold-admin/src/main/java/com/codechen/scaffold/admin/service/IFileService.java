package com.codechen.scaffold.admin.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author：Java陈序员
 * @date 2023-05-11 15:59
 * @description 文件服务接口
 */
public interface IFileService {

    public void init();

    public void sava(MultipartFile file);

    public Resource load(String fileName);

    public Stream<Path> load();

    public void clear();

}
