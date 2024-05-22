package com.codechen.scaffold.service.impl;

import com.codechen.scaffold.service.IFileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author：Java陈序员
 * @date 2023-05-11 16:01
 * @description 文件接口实现类
 */
@Service
public class IFileServiceImpl implements IFileService {

    private final Path path = Paths.get("E:\\file");

    @Override
    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void sava(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.path.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
        Path resolve = path.resolve(fileName);
        try {
            UrlResource resource = null;
            resource = new UrlResource(resolve.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("could not read the file.");
            }
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            throw new RuntimeException("Error:" + e.getMessage());
        }

    }

    @Override
    public Stream<Path> load() {
        try {
            return Files.walk(this.path, 1)
                    .filter(path1 -> !path1.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException("could not load the files.");
        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(this.path.toFile());
    }
}
