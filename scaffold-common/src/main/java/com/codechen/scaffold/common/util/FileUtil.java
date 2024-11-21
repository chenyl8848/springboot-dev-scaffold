package com.codechen.scaffold.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date 2023-05-09 16:59
 * @description 文件工具类
 */
public class FileUtil {

    public static Boolean isExist(String filePath) {
        return new File(filePath).exists();
    }

    public static Boolean isFile(String filePath) {
        return new File(filePath).isFile();
    }

    public static Boolean mkdirDirectory(File file) {
        File parentFile = file.getParentFile();

        if (parentFile != null) {
            return parentFile.mkdirs();
        }
        return false;
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    public static String getFileSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }

        int index = fileName.lastIndexOf(".");
        int length = fileName.length();
        if (index == -1 || index == length - 1) {
            return "";
        }

        return fileName.substring(index + 1, length);
    }

    public static Boolean deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.isDirectory()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

    public static Boolean deleteDirectory(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            return false;
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    deleteDirectory(files[i].getPath());
                }
            }
        }
        return file.delete();
    }

    public static List<File> getDirectoryFile(String filePath) {
        List<File> list = new ArrayList<>();
        File file = new File(filePath);

        if (file.isDirectory()) {
            list = Arrays.asList(file.listFiles());
        }

        return list;
    }

    public static List<String> getDirectoryFileName(String filePath) {
        return getDirectoryFile(filePath).stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public static void copy(File srcFile, File targetFile) {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile), 1024);
            outputStream = new BufferedOutputStream(new FileOutputStream(targetFile), 1024);

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readTextToString(String filePath) {
        File file = new File(filePath);
        StringBuffer stringBuffer = new StringBuffer();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(System.lineSeparator() + text);
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
}
