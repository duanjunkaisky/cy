package com.djk.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public class ExcelTest {

    public static List<List<Object>> getData(String inputFile) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        String line = null;
        List<List<Object>> list = new ArrayList<>();
        List<Object> item = new ArrayList<>();
        while (null != (line = bufferedReader.readLine())) {
            if (line.endsWith("集群")) {
                item = new ArrayList<>();
                list.add(item);
            }
            item.add(line);
        }
        return list;
    }

    public static LinkedList<List<String>> translateHead(List<String> headTemplate) {
        LinkedList<List<String>> customHead = new LinkedList<>();
        for (String headProperty : headTemplate) {
            customHead.add(Arrays.asList(new String[]{headProperty.split(":")[0]}));
        }
        return customHead;
    }

    public static CustomCellHandler customCellHandler(List<String> headTemplate) {
        CustomCellHandler customCellHandler = new CustomCellHandler();
        Map<String, Map<String, Object>> cellOption = new HashMap<>();
        for (String headProperty : headTemplate) {
            String[] split = headProperty.split(":");
            Map<String, Object> attr = new HashMap<>();
            attr.put("colWidth", split.length == 1 ? 15 : split[1]);
            cellOption.put(split[0], attr);
        }
        customCellHandler.setCellOption(cellOption);
        return customCellHandler;
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return true;
        }
        return false;
    }

    public static String getOutputFileName(String inputFile, String sheetName) {
        String outputFile = "";
        if (isWindows()) {
            String[] split = inputFile.split("\\\\");
            for (int i = 0; i < split.length - 1; i++) {
                outputFile += split[i] + "\\";
            }
        } else {
            String[] split = inputFile.split("/");
            outputFile += "/";
            for (int i = 0; i < split.length - 1; i++) {
                outputFile += split[i] + "/";
            }
        }
        outputFile += sheetName + ".xls";
        return outputFile;
    }

    public static HorizontalCellStyleStrategy getStyleStrategy(int headFont, int contentFont) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        // headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) headFont);
        // 字体样式
        headWriteFont.setFontName("宋体");
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 自动换行 true
        headWriteCellStyle.setWrapped(false);
        // 水平对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);// 下边框

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了
        // FillPatternType所以可以不指定
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SQUARES);
        // 背景白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) contentFont);
        // 字体样式
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 自动换行 true
        contentWriteCellStyle.setWrapped(false);
        // 水平对齐方式
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    public static void main(String[] args) throws Exception {
        String inputFile = "D:\\IdeaProjects\\cy\\core-common\\src\\main\\java\\com\\djk\\core\\utils\\111111.txt";
        String sheetName = "权限列表";
        List<String> headTemplate = Arrays.asList(new String[]{"地区:16", "资源详情:80", "资源描述:50", "数据等级:12", "敏感字段:40", "权限类型:12", "有效开始时间:16", "有效结束时间:16"});

        List<List<Object>> list = getData(inputFile);
        LinkedList<List<String>> customHead = translateHead(headTemplate);

        ExcelWriterBuilder write = EasyExcel.write(new FileOutputStream(getOutputFileName(inputFile, sheetName)));
        write.head(customHead);
        write.registerWriteHandler(customCellHandler(headTemplate));
        write.registerWriteHandler(getStyleStrategy(12, 12));
        ExcelWriterSheetBuilder sheet = write.sheet(sheetName);
        sheet.doWrite(list);
    }

}