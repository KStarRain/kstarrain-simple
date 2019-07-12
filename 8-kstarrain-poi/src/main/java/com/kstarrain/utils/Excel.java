package com.kstarrain.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel 工具类（未优化版）
 * 请了解清除大致用法之后再用，不然有坑
 * @author Kahle
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
		return readRow(rowNum, (int)row.getFirstCellNum(), (int)row.getLastCellNum());
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

	public Excel writeRow(Integer rowNum, List<?> rowContent) {
		int len = rowContent.size();
		Row row = currentSheet().createRow(rowNum);
		for (int i = 0; i < len; ++i) {
			setCellValue(row.createCell(i), rowContent.get(i));
		}
		return this;
	}

	public Excel write(OutputStream out) throws IOException {
		workBook.write(out);
		out.flush();
		return this;
	}

	private Object getCellValue(Cell cell) {
		Object cellValue = "";
		if (cell != null) {
			//TODO
//			System.out.println("====:"+cell.getRowIndex()+","+cell.getStringCellValue()+","+cell.getRow());
			
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
				case Cell.CELL_TYPE_FORMULA: {
					if (DateUtil.isCellDateFormatted(cell)) {
						cellValue = cell.getDateCellValue();
					} else {
						cellValue = cell.getNumericCellValue();
					} break;
				}
				case Cell.CELL_TYPE_BOOLEAN:
					cellValue = cell.getBooleanCellValue(); break;
				default :
					cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}

	private void setCellValue(Cell cell, Object value) {
		if (value == null) {
			cell.setCellValue("");
		} else if (value instanceof String) {
			cell.setCellValue((String)value);
		} else if (value instanceof RichTextString) {
			cell.setCellValue((RichTextString)value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		} else if (value instanceof Number) {
			cell.setCellValue(((Number)value).doubleValue());
		}else {
			cell.setCellValue(value.toString());
		}
	}

}
