package com.cyl.scaffold.core.util;

import org.ofdrw.converter.ConvertHelper;
import org.ofdrw.converter.GeneralConvertException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author cyl
 * @date 2023-05-09 17:05
 * @description ofd 操作工具类
 */
public class OfdUtil {

    /**
     * 将ofd转换为pdf
     *
     * @param originPath 源文件路径
     * @param pdfPath    目标文件路径
     */
    public static void ofdToPdf(String originPath, String pdfPath) {
        // 1. 文件输入路径
        Path src = Paths.get(originPath);
        // 2. 转换后文件输出位置
        Path dst = Paths.get(pdfPath);
        try {
            // 3. OFD转换PDF
            ConvertHelper.toPdf(src, dst);
            System.out.println("生成文档位置: " + dst.toAbsolutePath());
        } catch (GeneralConvertException e) {
            // pom引入相关模块GeneralConvertException 类型错误表明转换过程中发生异常
            e.printStackTrace();
        }
    }

}
