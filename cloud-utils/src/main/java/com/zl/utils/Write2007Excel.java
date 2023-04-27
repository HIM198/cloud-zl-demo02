package com.zl.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;


public class Write2007Excel {

	// 标题字体
	private XSSFFont titleFont = null; //2007格式

	// 标题样式
	private XSSFCellStyle titleStyle = null;//2007格式

	// 行信息内容样式
	private XSSFCellStyle contentStyle = null;//2007格式
	
	
	public XSSFWorkbook addSheet(XSSFWorkbook wb, String[] titleStrs,String sheetName)  {
		System.out.println("sheetName"+sheetName);
		setExcelStyle(wb);// 执行样式初始化
		XSSFSheet sheet = wb.createSheet(sheetName);//2007格式
		XSSFRow titleRow = sheet.createRow((short) 0);//2007格式
		titleRow.setHeightInPoints(20);// 20像素
		int titleCount = titleStrs.length;// 列数
		// 写标题
		for (int k = 0; k < titleCount; k++) {
			XSSFCell cell = titleRow.createCell((short) k); //2007格式
			cell.setCellStyle(titleStyle);// 设置标题样式
			cell.setCellType(CellType.STRING);
			cell.setCellValue(titleStrs[k]);
			sheet.setColumnWidth((short) k, (short) 5000);// 设置列宽
		}
		return wb;
	}

	/** 样式初始化 */
	public void setExcelStyle(XSSFWorkbook workBook) {
		// 设置列标题字体，样式
		titleFont = workBook.createFont();
		titleFont.setBold(true);
		// 标题列样式
		titleStyle = workBook.createCellStyle();
		titleStyle.setBorderTop(BorderStyle.THIN); // 设置边框
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setFont(titleFont);
		// 内容列样式
		contentStyle = workBook.createCellStyle();
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.LEFT);
	}

}
