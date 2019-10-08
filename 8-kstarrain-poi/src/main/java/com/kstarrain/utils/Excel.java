package com.kstarrain.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-09-06 14:27
 */
public class Excel {

	public enum Type {
		XLS(".xls"),
		XLSX(".xlsx"),
		LARGE_XLSX(".xlsx") //用于导出上万条数据，只支持写，不支持读
		;

		private String suffix;

		Type(String suffix) {
			this.suffix = suffix;
		}

		public String getSuffix() {
			return suffix;
		}

	}

	private Type type;
	private Workbook workBook;
	private Sheet sheet;

	Excel(Workbook workBook, Type type) {
		this.type = type;
        this.workBook = workBook;
	}

	public Workbook getWorkbook() {
		return workBook;
	}

	public String getSuffix() {
		return type.getSuffix();
	}

	public Integer getLastRowNum() {
		// get the last row number on the now select sheet
		// based 0, contained n
		return currentSheet().getLastRowNum();
	}

	public Excel createSheet() {
		this.sheet = workBook.createSheet();
		return this;
	}

	public Excel createSheet(String sheetname) {
		this.sheet = workBook.createSheet(sheetname);
		return this;
	}

	public Excel selectSheet(int index) {
		this.sheet = workBook.getSheetAt(index);
		return this;
	}

	public Excel selectSheet(String sheetname) {
		this.sheet = workBook.getSheet(sheetname);
		return this;
	}

	public Sheet currentSheet() {
		if (sheet == null) {
			throw new RuntimeException("Create or select sheet first.");
		}
		return sheet;
	}

	public List<?> readRow(Integer rowNum) {
		Row row = currentSheet().getRow(rowNum);
		if (row == null) {
			return new ArrayList<>();
		}
		return readRow(rowNum, (int)row.getLastCellNum());
	}

	public List<?> readRow(Integer rowNum, Integer colNum) {
		// get 0 to colNum cell (not contain colNum)
		return readRow(rowNum, 0, colNum);
	}

	public List<?> readRow(Integer rowNum, Integer firstCellNum, Integer lastCellNum) {
		// get now select sheet a line, rowNum begin 0
		// firstCellNum begin 0, not contain lastCellNum
		List<Object> rowContent = new ArrayList<>();
		Row row = currentSheet().getRow(rowNum);
		for (int i = firstCellNum; i < lastCellNum; i++) {
			rowContent.add(getCellValue(row.getCell(i)));
		}
		return rowContent;
	}

	public Excel writeRow(Integer rowIndex, List<?> rowContent, boolean isTitle) {
		Row row = currentSheet().createRow(rowIndex);

		CellStyle cellStyle = null;
		if (isTitle){
			cellStyle = this.createTitleCellStyle();
		}
		for (int columnIndex = 0; columnIndex < rowContent.size(); ++columnIndex) {
			setCellValue(columnIndex, row.createCell(columnIndex), cellStyle, rowContent.get(columnIndex));
		}
		return this;
	}


	private CellStyle createTitleCellStyle() {

		CellStyle style = workBook.createCellStyle();

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);              // 设置单元格水平方向对齐方式：居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);   // 设置单元格垂直方向对齐方式：居中

		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);      // 设置单元格填充背景颜色：天蓝色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);        // 设置单元格填充样式：

		style.setBorderTop(HSSFCellStyle.BORDER_THIN);               // 设置单元格顶部边框线条：细线
		style.setTopBorderColor(IndexedColors.RED.getIndex());       // 设置单元格顶部边框颜色：红

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);            // 设置单元格底部边框线条：细线
		style.setBottomBorderColor(IndexedColors.RED.getIndex());    // 设置单元格底部边框颜色：红

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);              // 设置单元格左边边框线条：细线
		style.setLeftBorderColor(IndexedColors.RED.getIndex());      // 设置单元格左边边框颜色：红

		style.setBorderRight(HSSFCellStyle.BORDER_THIN);             // 设置单元格右边边框线条：细线
		style.setRightBorderColor(IndexedColors.RED.getIndex());     // 设置单元格右边边框颜色：红


		// 生成一个字体
		Font font = workBook.createFont();
		font.setColor(HSSFColor.VIOLET.index);                       // 设置字体颜色
		font.setFontHeightInPoints((short) 12);                      // 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);                // 设置字体粗细
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}


	public Excel write(OutputStream out) throws IOException {
		workBook.write(out);
		out.flush();
		return this;
	}

	private Object getCellValue(Cell cell) {

		if (cell == null) {return "";}

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case Cell.CELL_TYPE_FORMULA: {
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue();
				} else {
					return cell.getNumericCellValue();
				}
			}
			case Cell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			default :
				return cell.getStringCellValue();
		}
	}

	private void setCellValue(int columnIndex, Cell cell, CellStyle cellStyle, Object value) {

		if (cellStyle != null){
			cell.setCellStyle(cellStyle);
		}

		int columnWidth = (sheet.getColumnWidth(columnIndex) - 184) / 256;

		if (value == null) {
			cell.setCellValue("");
		} else if (value instanceof String) {
			cell.setCellValue((String)value);
			this.autoSizeColumn(columnIndex, columnWidth, cell.getStringCellValue().getBytes().length);
		} else if (value instanceof RichTextString) {
			cell.setCellValue((RichTextString)value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
			this.autoSizeColumn(columnIndex, columnWidth, String.valueOf(cell.getBooleanCellValue()).getBytes().length);
		} else if (value instanceof Date) {

            CellStyle dateCellStyle = workBook.createCellStyle();
            dateCellStyle.setDataFormat(workBook.createDataFormat().getFormat("yyyy/mm/dd HH:mm:ss"));
            cell.setCellStyle(dateCellStyle);

            cell.setCellValue((Date) value);
			this.autoSizeColumn(columnIndex, columnWidth, String.valueOf(cell.getDateCellValue().getTime()).getBytes().length);
		} else if (value instanceof Number) {
			cell.setCellValue(((Number)value).doubleValue());
			this.autoSizeColumn(columnIndex, columnWidth, String.valueOf(cell.getNumericCellValue()).getBytes().length);
		}else {
			cell.setCellValue(value.toString());
			this.autoSizeColumn(columnIndex, columnWidth, cell.getStringCellValue().getBytes().length);
		}


	}

	// 列宽中文自适应
	private void autoSizeColumn(int columnIndex, int columnWidth, int length) {
		if (columnWidth < length) {
			columnWidth = length;
		}
		sheet.setColumnWidth(columnIndex, columnWidth * 256 + 184);
	}

}
