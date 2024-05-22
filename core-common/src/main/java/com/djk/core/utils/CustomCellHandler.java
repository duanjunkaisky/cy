package com.djk.core.utils;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class CustomCellHandler extends AbstractColumnWidthStyleStrategy {

    private Map<String, Map<String, Object>> cellOption;

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        Sheet sheet = writeSheetHolder.getSheet();
        if (aBoolean) {
            List<String> headNameList = head.getHeadNameList();
            String headColumn = headNameList.get(0);
            if (headColumn.toLowerCase().indexOf("id") != -1) {
                sheet.setColumnWidth(cell.getColumnIndex(), 13 * 256);
                if (headColumn.length() + 4 > 13) {
                    sheet.setColumnWidth(cell.getColumnIndex(), (headColumn.length() + 4) * 256);
                }
            } else if (headColumn.toLowerCase().indexOf("time") != -1) {
                sheet.setColumnWidth(cell.getColumnIndex(), 21 * 256);
                if (headColumn.length() + 4 > 21) {
                    sheet.setColumnWidth(cell.getColumnIndex(), (headColumn.length() + 4) * 256);
                }
            } else {
                sheet.setColumnWidth(cell.getColumnIndex(), (headColumn.length() + 4) * 256);
            }

            if (null != cellOption) {
                Map<String, Object> objectMap = cellOption.get(headColumn);
                if (null != objectMap) {
                    Object colName = objectMap.get("colName");
                    if (null != colName && !StringUtils.isEmpty(colName)) {
                        cell.setCellValue(String.valueOf(colName));
                    }
                    Object colWidth = objectMap.get("colWidth");
                    if (null != colWidth && !StringUtils.isEmpty(colWidth)) {
                        sheet.setColumnWidth(cell.getColumnIndex(), Integer.parseInt(String.valueOf(colWidth)) * 256);
                    }
                }
            }
        }
    }
}