package com.aigo.kt03airdemo.ui.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SaveToExcel {
	static HashMap map = new HashMap();

	/*
	 * 这个更全
	 */
	public static void main(String[] args) {
		try {
			// copyDateFormat(new File("c:\\a.xls"), 0, "c:\\copy of a.xls");
			writeExcelUseFormat("c:\\format.xls", "test");

			// buildNewFormTemplete(new File("c:/templete.xls"),new File(
			// "c:/buildNewFormTemplete.xls"));
			// modifyDirectly1(new File("c:/templete.xls"));
			// modifyDirectly2(new File("c:/templete.xls"));
			// copyDateAndFormat(new File("c:/a.xls"), 0, "c:/a2.xls");
		}
		catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}


	public static void writeExcelUseFormat(String outputFilePath, String outputFileSheetName) throws Exception {
		OutputStream os = new FileOutputStream(outputFilePath);// 输出的Excel文件URL
		WritableWorkbook wwb = Workbook.createWorkbook(os);// 创建可写工作薄
		WritableSheet sheet = wwb.createSheet(outputFileSheetName, 0);// 创建可写工作表

		sheet.addCell(new Label(0, 0, "号码"));
		sheet.addCell(new Label(1, 0, "有效期"));

		// 1.写入时间的数据格式
		jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd");
		jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(df);
		jxl.write.DateTime labelDTF = new jxl.write.DateTime(1, 1, new Date(), wcfDF); // 自定义格式
		sheet.addCell(labelDTF);

		// 2.字体样式
		// WritableFont()方法里参数说明：
		// 这个方法算是一个容器，可以放进去好多属性
		// 第一个: TIMES是字体大小，他写的是18
		// 第二个: BOLD是判断是否为斜体,选择true时为斜体
		// 第三个: ARIAL
		// 第四个: UnderlineStyle.NO_UNDERLINE 下划线
		// 第五个: jxl.format.Colour.RED 字体颜色是红色的
		jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);
		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
		wcfF.setWrap(true);// 自动换行
		wcfF.setAlignment(jxl.format.Alignment.CENTRE);// 把水平对齐方式指定为居中
		wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
		jxl.write.Label labelC = new jxl.write.Label(0, 1, "This is a Label cell", wcfF);
		sheet.addCell(labelC);

		// 3.添加带有formatting的Number对象
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
		jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
		jxl.write.Number labelNF = new jxl.write.Number(0, 2, 3.1415926, wcfN);
		sheet.addCell(labelNF);

		// 4.添加Boolean对象
		jxl.write.Boolean labelB = new jxl.write.Boolean(0, 3, false);
		sheet.addCell(labelB);

		// 5.设置一个注解
		WritableCellFeatures cellFeatures = new WritableCellFeatures();
		cellFeatures.setComment("添加Boolean对象");
		labelB.setCellFeatures(cellFeatures);

		// 6.单元格内换行
		WritableCellFormat wrappedText = new WritableCellFormat(WritableWorkbook.ARIAL_10_PT);
		wrappedText.setWrap(true);// 可换行的label样式
		Label label = new Label(4, 0, "测试,\012测试。。。", wrappedText); // "\012"强制换行
		sheet.addCell(label);

		// 7.数字的公式计算
		jxl.write.Number n = new jxl.write.Number(0, 9, 4.5);// A10
		sheet.addCell(n);
		n = new jxl.write.Number(1, 9, 8);// B10
		sheet.addCell(n);
		NumberFormat dp3 = new NumberFormat("#.###"); // 设置单元格里面的数字格式
		WritableCellFormat dp3cell = new WritableCellFormat(dp3);
		dp3cell.setWrap(true);
		Formula f = new Formula(2, 9, "(a10+b10)/2", dp3cell); // 设置C10公式
		sheet.addCell(f);
		f = new Formula(3, 9, "SUM(A10:B10)", dp3cell);// 设置D10公式
		sheet.addCell(f);

		// 8.设置sheet的样式
		sheet.getSettings().setProtected(true); // 设置xls的保护，单元格为只读的
		sheet.getSettings().setPassword("123"); // 设置xls的密码
		sheet.getSettings().setDefaultColumnWidth(10); // 设置列的默认宽度,2cm左右
		sheet.setRowView(3, 200);// 设置第4行高度
		sheet.setRowView(2, false);// 这样可以自动把行高扩展
		sheet.setColumnView(0, 300);// 设置第1列宽度，6cm左右
		sheet.mergeCells(0, 5, 1, 7);// 合并单元格：合并A6B8也就是1列6行 与 2列7行之间的矩形

		// 9.设置边框
		drawRect(sheet, 5, 6, 7, 6, BorderLineStyle.THICK, Colour.BLACK, null);

		sheet.mergeCells(1, 2, 3, 3);

		wwb.write();
		wwb.close();
		os.close();
	}

	public static void drawRect(WritableSheet sheet, int x, int y, int width, int height, BorderLineStyle style, Colour BorderColor, Colour bgColor) throws WriteException {
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				WritableCellFormat alignStyle = new WritableCellFormat(); // 单元格样式
				alignStyle.setAlignment(Alignment.CENTRE); // 设置对齐方式
				alignStyle.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置对齐方式
				if (h == 0) // 画上
					alignStyle.setBorder(Border.TOP, style, BorderColor);// 设置边框的颜色和样式

				if (w == 0) // 画左
					alignStyle.setBorder(Border.LEFT, style, BorderColor);// 设置边框的颜色和样式

				if (w == width - 1) // 画右
					alignStyle.setBorder(Border.RIGHT, style, BorderColor);// 设置边框的颜色和样式

				if (h == height - 1) // 画下
					alignStyle.setBorder(Border.BOTTOM, style, BorderColor);// 设置边框的颜色和样式
				// drawLine(sheet, x, y, Border.BOTTOM);
				if (bgColor != null) alignStyle.setBackground(bgColor); // 背静色
				Label mergelabel = new Label(x, y, "", alignStyle);
				// topleftXIndex, topleftYIndex, bottomRightXIndex,
				// bottomRightYIndex
				// sheet.mergeCells(2, 5, 10, 10);
				sheet.addCell(mergelabel);
				y++;
			}
			y -= height;
			x++;
		}
	}

}
