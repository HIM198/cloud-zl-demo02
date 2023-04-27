package com.zl.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
public class ExcelExport<T> {
	private String OUT_FILE_ENCODING = "ISO8859-1";
	private int index = 0;

	/**
	 * 无sheet名，无标题栏.
	 * 
	 * @param dataset
	 * @param out
	 */
	public void exportExcel(Collection<T> dataset, OutputStream out, String... keyStrings) {
		exportExcel("sheet1", null, dataset, out, "yyyy-MM-dd", keyStrings);
	}

	/**
	 * 无sheet名，有标题栏.
	 * 
	 * @param headers
	 * @param dataset
	 * @param out
	 */
	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String... keyStrings) {
		exportExcel("sheet1", headers, dataset, out, "yyyy-MM-dd", keyStrings);
	}

	/**
	 * 有sheet名，有标题栏.
	 * 
	 * @param sheetTitle
	 *            工作表名称
	 * @param headers
	 *            第一行表头
	 * @param dataset
	 *            数据集合
	 * @param out
	 *            输出流
	 * @param keyStrings
	 *            对应的T的参数名或者KEY
	 */
	public void exportExcel(String sheetTitle, String[] headers, Collection<T> dataset, OutputStream out, String... keyStrings) {
		exportExcel(sheetTitle, headers, dataset, out, "yyyy-MM-dd", keyStrings);
	}
	public void exportExcel2007(String sheetTitle, String[] headers, Collection<T> dataset, OutputStream out, String... keyStrings) {
		exportExcel2007(sheetTitle, headers, dataset, out, "yyyy-MM-dd", keyStrings);
	}

	public SXSSFWorkbook createSheet2007(SXSSFWorkbook workbook,String sheetTitle, String[] headers, Collection<T> dataset, String... keyStrings) {
		// 生成一个表格
		Sheet sheet = workbook.createSheet(sheetTitle);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(9);
		// 声明一个画图的顶级管理器
		Drawing patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// 遍历集合数据，产生数据行
		putDateset2Rows2007(headers,dataset, "yyyy-MM-dd", workbook, sheet, patriarch, keyStrings);
		return workbook;
	}
	public void exportExcel2007(SXSSFWorkbook workbook,OutputStream out){
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			workbook = null;
		}
	}
	
	/**
	 * 
	 * @param response
	 * @param sheetTitle
	 * @param headers
	 * @param dataset
	 * @param keyStrings
	 * @throws IOException
	 */
	public void exportExcel(HttpServletResponse response, String sheetTitle, String[] headers, Collection<T> dataset,
			String[] keyStrings) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((sheetTitle + ".xls").getBytes(), OUT_FILE_ENCODING) + "\"");
		exportExcel(sheetTitle, headers, dataset, response.getOutputStream(), "yyyy-MM-dd", keyStrings);
	}


	private void createHead(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers, String... keyStrings) {
		if (headers != null && headers.length > 0) {
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor((short) 20);
			font.setFontHeightInPoints((short) 9);
			font.setFontName("微软雅黑");
			style.setWrapText(true);
			// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
		}
	}
	
	private void createHead2007(Workbook workbook, Sheet sheet, String[] headers, String... keyStrings) {
		if (headers != null && headers.length > 0) {
			// 生成一个样式
			CellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成一个字体
			Font font = workbook.createFont();
			font.setColor((short) 20);
			font.setFontHeightInPoints((short) 9);
			font.setFontName("微软雅黑");
			style.setWrapText(true);
			// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			Row row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellType(CellType.STRING);
				//XSSFRichTextString text = new XSSFRichTextString(headers[i]);
				cell.setCellValue(headers[i]);
			}
		}
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * 增加了JAVABEAN 导出的列的设置
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */

	private void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern, String... keyStrings) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 产生表格标题行
		// 遍历集合数据，产生数据行
		putDateset2Rows(headers,dataset, pattern, workbook, sheet, patriarch, keyStrings);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			workbook = null;
			sheet = null;
			patriarch = null;
		}
	}
	
	private void exportExcel2007(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern, String... keyStrings) {
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(9);
		// 声明一个画图的顶级管理器
		Drawing patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// 遍历集合数据，产生数据行
		putDateset2Rows2007(headers,dataset, pattern, workbook, sheet, patriarch, keyStrings);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			workbook = null;
			sheet = null;
			patriarch = null;
		}
	}
	private void putDateset2Rows2007(String[] headers,Collection<T> dataset, String pattern, Workbook workbook, Sheet sheet, Drawing patriarch, String... keyStrings) {
		createHead2007(workbook, sheet, headers, keyStrings);
		if (dataset != null && dataset.size() > 0) {
			// 生成并设置一个样式
			CellStyle style2 = workbook.createCellStyle();
			style2.setBorderBottom(BorderStyle.THIN);
			style2.setBorderLeft(BorderStyle.THIN);
			style2.setBorderRight(BorderStyle.THIN);
			style2.setBorderTop(BorderStyle.THIN);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成另一个字体
			Font font2 = workbook.createFont();
			font2.setFontHeightInPoints((short)9);
			font2.setFontName("微软雅黑");
			//font2.setBold(true);
			// 把字体应用到当前的样式
			style2.setFont(font2);
			Iterator<T> it = dataset.iterator();
			boolean typeFlag = true;// 集合类型,如果是map则执行相关方法
			boolean mapType = false;// Collection内的集合类型,多种类型后用 case
			String[] keyArray = null;
			int j = 0;
			index = 1;
			while (it.hasNext()) {
				Row row = sheet.createRow(index);
				T t = (T) it.next();
				if (typeFlag) {
					if (t instanceof Map) {
						mapType = true;
					} else {
						typeFlag = false;
					}
				}
				if (mapType) {
					if (typeFlag) {
						typeFlag = false;
						if (keyStrings != null) {
							keyArray = (String[]) keyStrings;
						} else {
							Set<String> s = ((Map) t).keySet();
							keyArray = s.toArray(new String[s.size()]);
						}
					}

					for (int i = 0; i < keyArray.length; i++) {
						Object value = ((Map) t).get(keyArray[i]);
						Cell cell = row.createCell(i);
						cell.setCellStyle(style2);
						putOneRow2007(pattern, workbook, sheet, patriarch, row, i, cell, value);
					}

				} else {
					// 其它类型(Bean)操作继续,用 反射执行写入
					// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
					Field[] fields = t.getClass().getDeclaredFields();
					if (keyStrings != null) {
						keyArray = (String[]) keyStrings;
						if (keyArray != null && keyArray.length > 0) {
							try {
								String getMethodName = "";
								for (int i = 0; i < keyArray.length; i++) {
									Cell cell = row.createCell(i);
									cell.setCellStyle(style2);
									String[] ks = StringUtils.split(keyArray[i], '.');
									Class tCls = t.getClass();
									Object value = null;
									for (String k : ks) {
										getMethodName = "get" + k.substring(0, 1).toUpperCase() + k.substring(1);
										Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
										value = getMethod.invoke(value == null ? t : value, new Object[] {});
										// 找不到数据或者数据为空
										if (value == null) {
											value = "";
											break;
										}
										tCls = value.getClass();
									}
									putOneRow2007(pattern, workbook, sheet, patriarch, row, i, cell, value);
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}
						}
					} else {
						for (int i = 0; i < fields.length; i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(style2);
							Field field = fields[i];
							String fieldName = field.getName();
							String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
							try {
								Class tCls = t.getClass();
								Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
								Object value = getMethod.invoke(t, new Object[] {});
								putOneRow2007(pattern, workbook, sheet, patriarch, row, i, cell, value);
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();	
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}
						}
					}
				}
				index++;
			}
		}
	}
	
	/**
	 * 遍历集合数据，产生数据行.
	 * 
	 * @param dataset
	 * @param pattern
	 * @param workbook
	 * @param sheet
	 * @param style2
	 * @param patriarch
	 */
	private void putDateset2Rows(String[] headers,Collection<T> dataset, String pattern, HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFPatriarch patriarch, String... keyStrings) {
		HSSFRow row;
		createHead(workbook, sheet, headers, keyStrings);
		if (dataset != null && dataset.size() > 0) {
			// 生成并设置一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setBorderBottom(BorderStyle.THIN);
			style2.setBorderLeft(BorderStyle.THIN);
			style2.setBorderRight(BorderStyle.THIN);
			style2.setBorderTop(BorderStyle.THIN);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成另一个字体
			HSSFFont font2 = workbook.createFont();
			font2.setFontHeightInPoints((short)9);
			font2.setFontName("微软雅黑");
			font2.setBold(true);
			// 把字体应用到当前的样式
			style2.setFont(font2);
			Iterator<T> it = dataset.iterator();
			boolean typeFlag = true;// 集合类型,如果是map则执行相关方法
			boolean mapType = false;// Collection内的集合类型,多种类型后用 case
			String[] keyArray = null;
			int j = 0;
			index = 1;
			//createHead(workbook, sheet, headers, keyStrings);
			while (it.hasNext()) {
				if (index == 60001) {
					j++;
					String sheetname = sheet.getSheetName();
					sheet = workbook.createSheet(sheetname + j);
					createHead(workbook, sheet, headers, keyStrings);
					index = 1;
				}
				row = sheet.createRow(index);
				// 第一次判断Collection内数据类型，有且仅执行一次判断
				T t = (T) it.next();
				if (typeFlag) {
					if (t instanceof Map) {
						mapType = true;
					} else {
						typeFlag = false;
					}
				}
				if (mapType) {
					// 集合为map类型,执行map所用的方法
					if (typeFlag) {
						typeFlag = false;

						if (keyStrings != null) {
							keyArray = (String[]) keyStrings;
						} else {
							Set<String> s = ((Map) t).keySet();
							keyArray = s.toArray(new String[s.size()]);
						}
					}

					for (int i = 0; i < keyArray.length; i++) {
						Object value = ((Map) t).get(keyArray[i]);
						HSSFCell cell = row.createCell(i);
						cell.setCellStyle(style2);
						putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
					}

				} else {
					// 其它类型(Bean)操作继续,用 反射执行写入
					// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
					Field[] fields = t.getClass().getDeclaredFields();
					if (keyStrings != null) {
						keyArray = (String[]) keyStrings;
						if (keyArray != null && keyArray.length > 0) {
							try {
								String getMethodName = "";
								for (int i = 0; i < keyArray.length; i++) {
									HSSFCell cell = row.createCell(i);
									cell.setCellStyle(style2);
									String[] ks = StringUtils.split(keyArray[i], '.');
									Class tCls = t.getClass();
									Object value = null;
									for (String k : ks) {
										getMethodName = "get" + k.substring(0, 1).toUpperCase() + k.substring(1);
										Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
										value = getMethod.invoke(value == null ? t : value, new Object[] {});
										// 找不到数据或者数据为空
										if (value == null) {
											value = "";
											break;
										}
										tCls = value.getClass();
									}
									putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}
						}
					} else {
						for (int i = 0; i < fields.length; i++) {
							HSSFCell cell = row.createCell(i);
							cell.setCellStyle(style2);
							Field field = fields[i];
							String fieldName = field.getName();
							String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

							try {
								Class tCls = t.getClass();
								Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
								Object value = getMethod.invoke(t, new Object[] {});

								putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();	
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}

						}
					}
				}

				index++;
			}
		}
	}

	public void putDateset3Rows(Collection<T> dataset, String pattern, HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFPatriarch patriarch, Object... keyStrings) {
		HSSFRow row;
		if (dataset != null && dataset.size() > 0) {
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setBorderBottom(BorderStyle.THIN);
			style2.setBorderLeft(BorderStyle.THIN);
			style2.setBorderRight(BorderStyle.THIN);
			style2.setBorderTop(BorderStyle.THIN);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFFont font2 = workbook.createFont();
			font2.setFontHeightInPoints((short)9);
			font2.setFontName("微软雅黑");
			font2.setBold(true);
			style2.setFont(font2);
			Iterator<T> it = dataset.iterator();
			boolean typeFlag = true;// 集合类型,如果是map则执行相关方法
			boolean mapType = false;// Collection内的集合类型,多种类型后用 case
			String[] keyArray = null;
			int index_t = 1;
			while (it.hasNext()) {
				row = sheet.createRow(index_t);
				// 第一次判断Collection内数据类型，有且仅执行一次判断
				T t = (T) it.next();
				if (typeFlag) {
					if (t instanceof Map) {
						mapType = true;
					} else {
						typeFlag = false;
					}
				}
				if (mapType) {
					// 集合为map类型,执行map所用的方法
					if (typeFlag) {
						typeFlag = false;

						if (keyStrings != null) {
							keyArray = (String[]) keyStrings;
						} else {
							Set<String> s = ((Map) t).keySet();
							keyArray = s.toArray(new String[s.size()]);
						}
					}
					for (int i = 0; i < keyArray.length; i++) {
						Object value = ((Map) t).get(keyArray[i]);
						HSSFCell cell = row.createCell(i);
						// HSSFCell cell =
						// row.createCell(i,CellType.STRING);
						cell.setCellStyle(style2);
						putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
					}

				} else {
					// 其它类型(Bean)操作继续,用 反射执行写入
					// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
					Field[] fields = t.getClass().getDeclaredFields();
					if (keyStrings != null) {
						keyArray = (String[]) keyStrings;
						if (keyArray != null && keyArray.length > 0) {
							try {
								String getMethodName = "";
								for (int i = 0; i < keyArray.length; i++) {
									HSSFCell cell = row.createCell(i);
									cell.setCellStyle(style2);
									String[] ks = StringUtils.split(keyArray[i], '.');
									Class tCls = t.getClass();
									Object value = null;
									for (String k : ks) {
										getMethodName = "get" + k.substring(0, 1).toUpperCase() + k.substring(1);
										Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
										value = getMethod.invoke(value == null ? t : value, new Object[] {});
										// 找不到数据或者数据为空
										if (value == null) {
											value = "";
											break;
										}
										tCls = value.getClass();
									}
									putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}
						}
					} else {
						for (int i = 0; i < fields.length; i++) {
							HSSFCell cell = row.createCell(i);
							// HSSFCell cell =
							// row.createCell(i,CellType.STRING);
							cell.setCellStyle(style2);
							Field field = fields[i];
							String fieldName = field.getName();
							String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
							try {
								Class tCls = t.getClass();
								Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
								Object value = getMethod.invoke(t, new Object[] {});

								putOneRow(pattern, workbook, sheet, patriarch, row, i, cell, value);
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								continue;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} finally {
							}

						}
					}
				}
				index_t++;
			}
		}
	}

	/**
	 * 设置一行记录.
	 * 
	 * @param pattern
	 * @param workbook
	 * @param sheet
	 * @param patriarch
	 * @param row
	 * @param i
	 * @param cell
	 * @param value
	 */
	private void putOneRow(String pattern, HSSFWorkbook workbook, HSSFSheet sheet, HSSFPatriarch patriarch, HSSFRow row, int i,
			HSSFCell cell, Object value) {
		String textValue = null;
		if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			textValue = sdf.format(date);
		} else if(value instanceof Number){
			cell.setCellType(CellType.NUMERIC);
			//textValue = value != null ? value.toString() : "";
			cell.setCellValue(new Double(value != null ? value.toString() : ""));
			return ;
		}else if (value instanceof byte[]) {
			// 有图片时，设置行高为60px;
			row.setHeightInPoints(60);
			// 设置图片所在列宽度为80px,注意这里单位的一个换算
			sheet.setColumnWidth(i, (short) (35.7 * 80));
			// sheet.autoSizeColumn(i);
			byte[] bsValue = (byte[]) value;
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
			anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
			patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
		} else {
			// 其它数据类型都当作字符串简单处理
			textValue = value != null ? value.toString() : "";
		}
		// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
		if (textValue != null) {
			cell.setCellType(CellType.STRING);
			cell.setCellValue(textValue);
		}
	}
	private void putOneRow2007(String pattern, Workbook workbook, Sheet sheet, Drawing patriarch, Row row, int i,Cell cell, Object value) {
		String textValue = null;
		if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			textValue = sdf.format(date);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(textValue != null ? textValue : "");
		} else if(value instanceof Number){
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(new Double(value != null ? value.toString() : ""));
		}else if (value instanceof byte[]) {
			row.setHeightInPoints(60);
			sheet.setColumnWidth(i, (short) (35.7 * 80));
			byte[] bsValue = (byte[]) value;
			XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
			anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
			patriarch.createPicture(anchor, workbook.addPicture(bsValue, SXSSFWorkbook.PICTURE_TYPE_JPEG));
		} else {
			textValue = value != null ? value.toString() : "";
			cell.setCellType(CellType.STRING);
			cell.setCellValue(textValue);
		}
		
	}
	

	/**
	 * 导出到Excel方法
	 * 
	 * @param title
	 *            String[] 标题数组
	 * @param list
	 *            需要导出的数据
	 * @param model
	 */
	public InputStream execute(String[] title, List<T> list, Object model) {
		HSSFWorkbook wk = new HSSFWorkbook();
		HSSFSheet sheet = wk.createSheet("sheet1");

		HSSFRow row = sheet.createRow(0);

		HSSFCell cell;

		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}

		for (int i = 0; i < list.size(); ++i) {
			model = list.get(i);
			row = sheet.createRow(i + 1);
			Method[] method = model.getClass().getMethods();
			int num = 0;
			for (int j = 0; j < method.length; j++) {
				if (method[j].getName().substring(0, 3).equals("get") && method[j].getName() != "getClass") {
					Method m;
					try {
						m = model.getClass().getMethod(method[j].getName());
						Object result = m.invoke(model);
						cell = row.createCell(num++);
						if (result != null) {
							cell.setCellValue(new HSSFRichTextString(result.toString()));
						} else {
							cell.setCellValue(new HSSFRichTextString("-"));
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}
			}

		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			wk.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();

		InputStream is = new ByteArrayInputStream(content);
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	public String getOUT_FILE_ENCODING() {
		return OUT_FILE_ENCODING;
	}

	public void setOUT_FILE_ENCODING(String oUT_FILE_ENCODING) {
		OUT_FILE_ENCODING = oUT_FILE_ENCODING;
	}


	public void exportExcel(List listSheetname, List<String[]> listHeaders,
			List<Collection<T>> listList, ServletOutputStream outputStream, List<String[]> listKeys) {
		// TODO Auto-generated method stub
		exportExcel(listSheetname,listHeaders,listList,outputStream,"yyyy-MM-dd",listKeys);
	}
	private void exportExcel(List listSheetname, List<String[]> listHeaders, List<Collection<T>> listList, OutputStream out, String pattern,
			List<String[]> listKeys) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 0; i < listSheetname.size(); i++) {
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(listSheetname.get(i).toString());
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			//createHead(workbook, sheet, listHeaders.get(i), listKeys.get(i));
			putDateset2Rows(listHeaders.get(i),listList.get(i), pattern, workbook, sheet, patriarch, listKeys.get(i));
		}
		try {
			workbook.write(out);
			//sheet = null;
			//patriarch = null;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			workbook = null;
				//sheet = null;
				//patriarch = null;

		}
	}
	
	private void exportExcel2007(List listSheetname, List<String[]> listHeaders, List<Collection<T>> listList, OutputStream out, String pattern,
			List<String[]> listKeys) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		for (int i = 0; i < listSheetname.size(); i++) {
			Sheet sheet = workbook.createSheet(listSheetname.get(i).toString());
			sheet.setDefaultColumnWidth(20);
			Drawing patriarch = sheet.createDrawingPatriarch();
			putDateset2Rows2007(listHeaders.get(i),listList.get(i), pattern, workbook, sheet, patriarch, listKeys.get(i));
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			workbook = null;
		}
	}
	private void createHead(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers) {
		if (headers != null && headers.length > 0) {
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor((short) 20);
			font.setFontHeightInPoints((short) 9);
			font.setFontName("微软雅黑");
			style.setWrapText(true);
			// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
		}
	}
	public void downExcel(List listSheetname, List<String[]> listHeaders,ServletOutputStream outputStream) {
		downloadExcel(listSheetname,listHeaders, outputStream);
	}
	private void downloadExcel(List listSheetname, List<String[]> listHeaders,  OutputStream out){
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 0; i < listSheetname.size(); i++) {
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(listSheetname.get(i).toString());
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			//createHead(workbook, sheet, listHeaders.get(i), listKeys.get(i));
			createHead(workbook, sheet, listHeaders.get(i));
		}
		try {
			workbook.write(out);
			//sheet = null;
			//patriarch = null;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//workbook = null;
				//sheet = null;
				//patriarch = null;

		}
	}
	public void exportExcel2007(List listSheetname, List<String[]> listHeaders,
			List<Collection<T>> listList, ServletOutputStream outputStream, List<String[]> listKeys) {
		// TODO Auto-generated method stub
		exportExcel2007(listSheetname,listHeaders,listList,outputStream,"yyyy-MM-dd",listKeys);
	}
}
