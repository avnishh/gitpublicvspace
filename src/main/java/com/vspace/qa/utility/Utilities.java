package com.vspace.qa.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.vspace.qa.base.Base;


public class Utilities extends Base {



	public  static void getScreenShot(String fileName)  {
		String fileNam = fileName; 
		TakesScreenshot ts = (TakesScreenshot)driver;
		File sourceFile= ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"\\Screenshots\\"+fileNam+".png");

		try {
			FileUtils.copyFile(sourceFile,destFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}


	public static String readExcelCellData( String excelFileName, int rowNum, int cellNum) {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\vspace\\qa\\testdata\\"+excelFileName+".xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFWorkbook Book = null;
		try {
			Book = new XSSFWorkbook(fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		XSSFSheet Sheet = Book.getSheetAt(0);
		Row row = Sheet.getRow(rowNum-1);
		Cell cell=row.getCell(cellNum-1);
		//System.out.println(cell);

		if (cell != null) { 
			return cell.getStringCellValue(); } 
		else {
			String s= ""; return s; }

		//return cell.getStringCellValue();

	}

	public static int getRowNum(String data) {
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\vspace\\qa\\testdata\\"+"data.xlsx");
			XSSFWorkbook book = new XSSFWorkbook(fs);     
			XSSFSheet sheet = book.getSheetAt(0);
			// Sheet sheet = workbook.getSheet(sheetName);

			// Iterate through rows to find the matching string in the first column
			for (Row row : sheet) {
				Cell cell = row.getCell(0); // First column
				if (cell != null && cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(data)) {
					return row.getRowNum() + 1; // Adding 1 to make it 1-based index
				}
			}

			book.close();
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1; // Return -1 if the string is not found


	}

	public static Object[][] getTestData(String excelFileName,String sheetName) {
		FileInputStream file = null;
		XSSFWorkbook book = null; ;
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\vspace\\qa\\testdata\\"+excelFileName+".xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = new XSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = book.getSheet(sheetName);
		// we dont know data in excel is string or int so we take object array it can take any type of data
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		//System.out.println(sheet.getLastRowNum() + "--------" + sheet.getRow(0).getLastCellNum());

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

				if ( sheet.getRow(i+1).getCell(k)!= null) { 
					data[i][k] = sheet.getRow(i+1).getCell(k).toString();
				} 
				else {
					String s= "";
					data[i][k] =s;
				}

				//System.out.println(data[i][k]);

			}}
		return data;
	}


}


