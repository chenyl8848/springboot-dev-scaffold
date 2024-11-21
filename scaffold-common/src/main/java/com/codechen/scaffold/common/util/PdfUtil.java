package com.codechen.scaffold.common.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author：Java陈序员
 * @date 2023-05-09 17:00
 * @description pdf 操作工具类
 */
public class PdfUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * pdf 添加水印
     *
     * @param inputFile     源文件地址
     * @param outputFile    添加水印后的文件地址
     * @param waterMarkName 水印
     * @param opacity       透明度
     * @param fontsize      字体大小
     * @param angle         角度
     * @param heightdensity 高度
     * @param widthdensity  宽度
     * @param cover         是否覆盖
     * @return
     */
    public static boolean pdfAddWaterMark(String inputFile,
                                          String outputFile,
                                          String waterMarkName,
                                          float opacity,
                                          int fontsize,
                                          int angle,
                                          int heightdensity,
                                          int widthdensity,
                                          boolean cover) {
        if (!cover) {
            File file = new File(outputFile);
            if (file.exists()) {
                return true;
            }
        }
        File file = new File(inputFile);
        if (!file.exists()) {
            return false;
        }

        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            int interval = -5;
            reader = new PdfReader(inputFile);
            stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            //这里是透明度设置
            gs.setFillOpacity(opacity);
            //这里是条纹不透明度
            gs.setStrokeOpacity(0.2f);
            int total = reader.getNumberOfPages() + 1;
            LOGGER.info("Pdf页数：{}", reader.getNumberOfPages());
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH = 0;
            int textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            // 字符串的高 只和字体有关
            textH = metrics.getHeight();
            // 字符串的宽
            textW = metrics.stringWidth(label.getText());
            PdfContentByte under;

            // 这个循环是确保每一张PDF都加上水印
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                under = stamper.getOverContent(i);  //在内容上方添加水印
                //under = stamper.getUnderContent(i);  //在内容下方添加水印
                under.saveState();
                under.setGState(gs);
                under.beginText();
                //under.setColorFill(BaseColor.PINK);  //添加文字颜色  不能动态改变 放弃使用
                under.setFontAndSize(base, fontsize); //这里是水印字体大小
                for (int height = textH; height < pageRect.getHeight() * 2; height = height + textH * heightdensity) {
                    for (int width = textW; width < pageRect.getWidth() * 1.5 + textW; width = width + textW * widthdensity) {
                        // rotation:倾斜角度
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW, height - textH, angle);
                    }
                }
                //添加水印文字
                under.endText();
            }
            LOGGER.info("添加水印成功！");
            return true;
        } catch (IOException e) {
            LOGGER.error("添加水印失败！错误信息为: {}", e);
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            LOGGER.error("添加水印失败！错误信息为: {}", e);
            e.printStackTrace();
            return false;
        } finally {
            //关闭流
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
        }

    }

    /**
     * pdf 添加图片水印
     *
     * @param inputPdfFile  源文件地址
     * @param outPdfFile    添加水印后的文件地址
     * @param markImagePath 图片地址
     * @param imgWidth      水印宽度
     * @param imgHeight     水印高度
     * @throws Exception
     */
    public static void pdfAddImageMark(String inputPdfFile,
                                       String outPdfFile,
                                       String markImagePath,
                                       int imgWidth,
                                       int imgHeight) {
        try {
            PdfReader reader = new PdfReader(inputPdfFile, "PDF".getBytes());
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

            PdfContentByte under;

            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);// 透明度设置

            Image img = Image.getInstance(markImagePath);// 插入图片水印

            img.setAbsolutePosition(imgWidth, imgHeight); // 坐标
//        img.setRotation(-20);// 旋转 弧度
//        img.setRotationDegrees(45);// 旋转 角度
            // img.scaleAbsolute(200,100);//自定义大小
            // img.scalePercent(50);//依照比例缩放

            int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
            for (int i = 1; i <= pageSize; i++) {
                under = stamp.getUnderContent(i);// 水印在之前文本下
                // under = stamp.getOverContent(i);//水印在之前文本上
                under.setGState(gs1);// 图片水印 透明度
                under.addImage(img);// 图片水印
            }

            stamp.close();// 关闭
            // // 删除不带水印文件
//		File tempfile = new File(InPdfFile);
//		if(tempfile.exists()) {
//			tempfile.delete();
//		}

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * pdf 添加文字水印
     *
     * @param inputPdfFile
     * @param outPdfFile
     * @param textMark
     * @param textWidth
     * @param textHeight
     * @throws Exception
     */
    public static void pdfAddTextMark(String inputPdfFile,
                                      String outPdfFile,
                                      String textMark,
                                      int textWidth,
                                      int textHeight) {
        try {
            PdfReader reader = new PdfReader(inputPdfFile, "PDF".getBytes());
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

            PdfContentByte under;

            BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", "Identity-H", true);// 使用系统字体

            int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
            for (int i = 1; i <= pageSize; i++) {
                under = stamp.getUnderContent(i);// 水印在之前文本下
                // under = stamp.getOverContent(i);//水印在之前文本上
                under.beginText();
                under.setColorFill(BaseColor.GRAY);// 文字水印 颜色
                under.setFontAndSize(font, 38);// 文字水印 字体及字号
                under.setTextMatrix(textWidth, textHeight);// 文字水印 起始位置
                under.showTextAligned(Element.ALIGN_CENTER, textMark, textWidth, textHeight, 45);
                under.endText();
            }

            stamp.close();// 关闭

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * pdf 加密
     *
     * @param filePath 源文件
     * @param savePath 加密后的文件
     * @param password 密码
     * @return
     */
    public static boolean pdfEncrypt(String filePath, String savePath, String password) {
        try {
            PdfReader reader = new PdfReader(filePath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(savePath));

            //ALLOW_PRINTING	文档允许打印
            //ALLOW_DEGRADED_PRINTING	允许用户打印文档，但不提供allow_printing质量（128位加密）
            //ALLOW_MODIFY_CONTENTS	允许用户修改内容，例如 更改页面内容，或插入或删除页
            //ALLOW_ASSEMBLY	允许用户插入、删除和旋转页面和添加书签。页面的内容不能更改，除非也授予allow_modify_contents权限，（128位加密）
            //ALLOW_COPY	允许用户复制或以其他方式从文档中提取文本和图形，包括使用辅助技术。例如屏幕阅读器或其他可访问设备
            //ALLOW_SCREENREADERS	允许用户提取文本和图形以供易访问性设备使用，（128位加密）
            //ALLOW_MODIFY_ANNOTATIONS	允许用户添加或修改文本注释和交互式表单字段
            //ALLOW_FILL_IN	允许用户填写表单字段，（128位加密）

            //访问者密码，拥有者密码(权限密码让pdf文件无法被修改)，访问者权限，加密方式。
            stamper.setEncryption("123456".getBytes(),
                    password.getBytes(),
                    PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_DEGRADED_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);

            stamper.close();
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
