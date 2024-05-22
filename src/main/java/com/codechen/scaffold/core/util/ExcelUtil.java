package com.codechen.scaffold.core.util;

import com.alibaba.fastjson.util.TypeUtils;
import com.codechen.scaffold.core.annotation.EnableExport;
import com.codechen.scaffold.core.annotation.ExportField;
import com.codechen.scaffold.core.annotation.ImportIndex;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author：Java陈序员
 * @date 2023-05-09 16:59
 * @description excel 导入导出工具类
 */
public class ExcelUtil {


    /**
     * 是否含有标题
     */
    private static boolean isContainTitle = true;

    /**
     * excel 导入
     * @param excelFile excel文件
     * @param clazz 实体类
     * @return
     */
    public static List<?> importExcel(File excelFile, Class clazz) {
        List<Object> list = new ArrayList<>();

        // 1.创建输入流，读取 excel
        InputStream inputStream = null;
        Sheet sheet = null;

        try {
            inputStream = new FileInputStream(excelFile.getAbsolutePath());

            if (inputStream != null) {
                Workbook workbook = WorkbookFactory.create(inputStream);

                // 默认只获取第一个工作表
                sheet = workbook.getSheetAt(0);

                if (sheet != null) {
                    int i = 2;
                    String values[];

                    Row row = sheet.getRow(i);
                    while (row != null) {
                        // 获取单元格数目
                        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                        values = new String[physicalNumberOfCells];

                        for (int j = 0; j < physicalNumberOfCells; j++) {
                            Cell cell = row.getCell(j);

                            if (cell != null) {
                                // 设置单元格内容类型
                                cell.setCellType(CellType.STRING);
                                // 获取单元格值
                                String cellValue = (cell.getStringCellValue() == null) ? null : cell.getStringCellValue();

                                values[j] = cellValue;
                            }
                        }

                        Field[] declaredFields = clazz.getDeclaredFields();
                        Object instance = clazz.newInstance();

                        for (Field field : declaredFields) {
                            if (field.isAnnotationPresent(ImportIndex.class)) {
                                ImportIndex importIndex = field.getAnnotation(ImportIndex.class);
                                int index = importIndex.index();

                                field.setAccessible(true);
                                Object value = TypeUtils.cast(values[index], field.getType(), null);
                                field.set(instance, value);
                            }
                        }

                        list.add(instance);
                        i++;
                        row = sheet.getRow(i);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * excel 导出
     * @param response 响应
     * @param exportDataList 导出数据
     * @param clazz 导出实体类
     */
    public static void exportExcel(HttpServletResponse response, List exportDataList, Class clazz) {
        exportExcel(response, exportDataList, clazz, null);
    }

    /**
     * excel 导出
     *
     * @param response
     * @param exportDataList 导出数据集合
     * @param clazz          导出实体
     * @param columnTitle    excel 标题
     */
    public static void exportExcel(HttpServletResponse response, List exportDataList, Class clazz, String columnTitle) {
        try {
            if (clazz.isAnnotationPresent(EnableExport.class)) {
                EnableExport enableExport = (EnableExport) clazz.getAnnotation(EnableExport.class);

                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(enableExport.fileName(), "UTF-8"));
                ExcelUtil.exportExcel(response.getOutputStream(), exportDataList, clazz, null, columnTitle);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * excel 导出
     *
     * @param outputStream  输出流
     * @param exportData    导出数据集合
     * @param clazz         导出实体
     * @param selectListMap 下拉列表
     * @param excelTitle    excel 标题
     */
    public static void exportExcel(OutputStream outputStream,
                                   List exportData,
                                   Class clazz,
                                   Map<Integer, Map<String, String>> selectListMap,
                                   String excelTitle) {
        // 1.创建 Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2.建立表
        HSSFSheet sheet = workbook.createSheet();

        sheet.setDefaultRowHeight((short) (20 * 20));

        // 3.检查当前 pojo 是否允许导出
        if (clazz.isAnnotationPresent(EnableExport.class)) {
            EnableExport enableExport = (EnableExport) clazz.getAnnotation(EnableExport.class);
            // 所有标题名称
            List<String> cloumnNameList = new ArrayList<>();
            // 所有要导出的字段
            List<Field> fieldList = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ExportField.class)) {
                    ExportField exportField = field.getAnnotation(ExportField.class);
                    cloumnNameList.add(exportField.columnName());
                    fieldList.add(field);
                }
            }

            // 4.设置每列的宽度
            for (int i = 0; i < fieldList.size(); i++) {
                sheet.setColumnWidth(i, fieldList.get(i).getAnnotation(ExportField.class).columnWidth() * 20);
            }

            HSSFRow hssfRow = null;
            HSSFCell hssfCell = null;

            // 5.绘制表头
            String fileName = enableExport.fileName();
            if (excelTitle != null) {
                fileName = excelTitle;
            }

            // 6.绘制标题
            if (excelTitle != null && !excelTitle.equals("")) {
                isContainTitle = true;
                createTitle(workbook, sheet, hssfRow, hssfCell, cloumnNameList.size() - 1, fileName);
            } else {
                isContainTitle = false;
            }

            // 7.创建标题行（表头）
            createHead(workbook, sheet, hssfRow, hssfCell, cloumnNameList);

            try {
                HSSFCellStyle cellStyle = getBasicCellStyle(workbook);

                // 8.插入内容
                int i = 0;

                for (Object object : exportData) {
                    if (isContainTitle) {
                        hssfRow = sheet.createRow(i + 2);
                    } else {
                        hssfRow = sheet.createRow(i + 1);
                    }
                    // 设置每列的宽度
                    for (int j = 0; j < fieldList.size(); j++) {
                        Field field = fieldList.get(j);
                        field.setAccessible(true);
                        Object value = field.get(object);
                        setCellValue(value, hssfCell, hssfRow, cellStyle, j);
                    }
                    i++;
                }

                // 9.创建下拉列表
                createDataValidation(sheet, selectListMap);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();

            } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void createDataValidation(HSSFSheet sheet, Map<Integer, Map<String, String>> selectListMap) {
        if (selectListMap != null) {
            for (Map.Entry<Integer, Map<String, String>> entry : selectListMap.entrySet()) {
                Integer key = entry.getKey();
                Map<String, String> value = entry.getValue();
                // 第几列校验（0开始）key 数据源数组value
                if (value.size() > 0) {
                    int i = 0;
                    String[] valueArr = new String[value.size()];
                    for (Map.Entry<String, String> ent : value.entrySet()) {
                        valueArr[i] = ent.getValue();
                        i++;
                    }
                    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(2, 65535, key, key);
                    DataValidationHelper helper = sheet.getDataValidationHelper();
                    DataValidationConstraint constraint = helper.createExplicitListConstraint(valueArr);
                    DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
                    //处理Excel兼容性问题
                    if (dataValidation instanceof XSSFDataValidation) {
                        dataValidation.setSuppressDropDownArrow(true);
                        dataValidation.setShowErrorBox(true);
                    } else {
                        dataValidation.setSuppressDropDownArrow(false);
                    }
                    dataValidation.setEmptyCellAllowed(true);
                    dataValidation.setShowPromptBox(true);
                    dataValidation.createPromptBox("提示", "只能选择下拉框里面的数据");
                    sheet.addValidationData(dataValidation);
                }
            }
        }
    }

    private static void setCellValue(Object value, HSSFCell hssfCell, HSSFRow hssfRow, HSSFCellStyle cellStyle, int cellIndex) {
        String valueStr = String.valueOf(value);
        hssfCell = hssfRow.createCell(cellIndex);
        //暂时认为数字类型不会有下拉列表
        if (isNumeric(valueStr)) {
            hssfCell.setCellStyle(cellStyle);
            hssfCell.setCellType(CellType.NUMERIC);
            hssfCell.setCellValue(Double.valueOf(valueStr));
        } else {
            hssfCell.setCellStyle(cellStyle);
            hssfCell.setCellType(CellType.STRING);
            hssfCell.setCellValue(valueStr);
        }
    }

    private static boolean isNumeric(String valueStr) {
        Pattern pattren =
                Pattern.compile("[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        if (valueStr != null && !"".equals(valueStr.trim())) {
            Matcher matcher = pattren.matcher(valueStr);
            if (matcher.matches()) {
                if (!valueStr.contains(".") && valueStr.startsWith("0")) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private static void createHead(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow hssfRow, HSSFCell hssfCell, List<String> cloumnNameList) {
        // 插入标题行
        if (isContainTitle) {
            hssfRow = sheet.createRow(1);
        } else {
            hssfRow = sheet.createRow(0);
        }
        for (int i = 0; i < cloumnNameList.size(); i++) {
            hssfCell = hssfRow.createCell(i);

            hssfCell.setCellStyle(getTitleCellStyle(workbook));
            hssfCell.setCellType(CellType.STRING);
            hssfCell.setCellValue(cloumnNameList.get(i));
        }
    }

    private static void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow hssfRow, HSSFCell hssfCell, int i, String fileName) {
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, i);

        sheet.addMergedRegion(cellAddresses);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellAddresses, sheet);

        hssfRow = sheet.getRow(0);
        hssfCell = hssfRow.getCell(0);

        hssfCell.setCellStyle(getTitleCellStyle(workbook));
        hssfCell.setCellType(CellType.STRING);
        hssfCell.setCellValue(fileName);


    }

    private static CellStyle getTitleCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle hssfCellStyle = getBasicCellStyle(workbook);
        // TODO:设置背景色
//        hssfCellStyle.setFillForegroundColor();
        hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return null;
    }

    private static HSSFCellStyle getBasicCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        hssfCellStyle.setBorderLeft(BorderStyle.THIN);
        hssfCellStyle.setBorderRight(BorderStyle.THIN);
        hssfCellStyle.setBorderTop(BorderStyle.THIN);
        hssfCellStyle.setBorderBottom(BorderStyle.THIN);
        hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        hssfCellStyle.setWrapText(true);
        return hssfCellStyle;
    }
}
