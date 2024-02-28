package com.vspace.qa.utility;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.vspace.qa.base.Base;

public class ExtentReportListener extends Base implements ITestListener{


	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest test;
	static String testDataValue;
	//Utilities util;


	/*
	 * private static ThreadLocal<String> testData = new ThreadLocal<>();
	 * 
	 * public static void setTestData(String data) { testData.set(data);
	 * testDataValue = testData.get();
	 * //System.out.println("Test Data in onTestSkipped: " + testDataValue); }
	 */

	public void configureReport() {

		spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\extentreport\\report.html");
		spark.config().setDocumentTitle("Vspace Automation Report");
		spark.config().setReportName("Automation Test execution report");
		spark.config().setTheme(Theme.STANDARD);
		//spark.config().setTimeStampFormat("");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		//System.out.println("i am in before test");


	}	

	@Override		
	public void onFinish(ITestContext Result) 					
	{		
		extent.flush();      		
	}		

	@Override		
	public void onStart(ITestContext Result)					
	{		
		configureReport();
		//System.out.println("I am in on start");
	}		

	@Override		
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result)					
	{		

	}		

	// When Test case get failed, this method is called.		
	@Override		
	public void onTestFailure(ITestResult Result) 					
	{		
		//String td = (String) Result.getAttribute("td");
		//String er = (String) Result.getAttribute("er");
		Utilities.getScreenShot(Result.getName());
		
		
		String er = null;
		String td = null;
		String[] arr = Result.getName().split("_");
		System.out.println(arr[0]);
		int row=Utilities.getRowNum(arr[0]);
        
		try {
			er= Utilities.readExcelCellData(row, 7);
			td= Utilities.readExcelCellData(row, 2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test = extent.createTest(Result.getName(),td);
		

		test.log(Status.FAIL, MarkupHelper.createLabel(er,ExtentColor.RED));
		//test.fail(MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+Result.getName()+".png", "provide info").build());
		test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+Result.getName()+".png").build());


	}		

	// When Test case get Skipped, this method is called.		
	@Override		
	public void onTestSkipped(ITestResult Result)					
	{		
		String[] methodsDependedUpon = Result.getMethod().getMethodsDependedUpon();
		String er = null;
		String td = null;
		String[] arr = Result.getName().split("_");
		System.out.println(arr[0]);
		int row=Utilities.getRowNum(arr[0]);
        
		try {
			er= Utilities.readExcelCellData(row, 7);
			td= Utilities.readExcelCellData(row, 2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test = extent.createTest(Result.getName(),td);
		test.log(Status.SKIP, MarkupHelper.createLabel(er,ExtentColor.ORANGE));
		/*if(testDataValue.equals("Login functionality not working")) {
		test.info(MarkupHelper.createLabel("Skipped as Login functionality not working", ExtentColor.GREY));}
		else {
		test.info(MarkupHelper.createLabel("Website is not reachable", ExtentColor.GREY));}
		}*/
		if(!isWebsiteReachable()) {
			test.info(MarkupHelper.createLabel("Website is not reachable", ExtentColor.GREY));
		}


		else if(methodsDependedUpon.length > 0) {
			for (String methodName : methodsDependedUpon) {
				//System.out.println(methodName);
				String arr1[] = methodName.split("\\.");
				// System.err.println(arr.length);
				test.info(MarkupHelper.createLabel("skipped because dependent test case:"+arr1[arr1.length-1]+" is failed", ExtentColor.GREY));

				//System.out.println();
			}
		}
		else {
			test.info(MarkupHelper.createLabel("Some error occured.", ExtentColor.GREY));
		}
	}


	//String testDataValue = testData.get();
	// System.out.println("Test Data in onTestSkipped: " + testDataValue);




	// When Test case get Started, this method is called.		
	@Override		
	public void onTestStart(ITestResult Result)					
	{		
		//System.out.println(Result.getName()+" test case started");					
	}		

	// When Test case get passed, this method is called.		
	@Override		
	public void onTestSuccess(ITestResult Result)					
	{		
		//System.out.println("The name of the testcase passed is :"+Result.getName());
	//	String td = (String) Result.getAttribute("td");
		
		String er = null;
		String td = null;
		String[] arr = Result.getName().split("_");
		System.out.println(arr[0]);
		int row=Utilities.getRowNum(arr[0]);
        
		try {
			er= Utilities.readExcelCellData(row, 7);
			td= Utilities.readExcelCellData(row, 2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test = extent.createTest(Result.getName(),td);

		//test.log(Status.PASS, MarkupHelper.createLabel("Name of the passed testcase is : "+Result.getName(),ExtentColor.GREEN));
		test.assignCategory("Smoke Suite");
		test.log(Status.PASS, MarkupHelper.createLabel(er,ExtentColor.GREEN));

	}		

}			

