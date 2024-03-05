package com.vspace.qa.utility;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	String projectDirectory=System.getProperty("user.dir");
	DateTimeFormatter dtf;
	LocalDateTime now;
	WebEventlistener eventListener;
	/*
	 * private static ThreadLocal<String> testData = new ThreadLocal<>();
	 * 
	 * public static void setTestData(String data) { testData.set(data);
	 * testDataValue = testData.get();
	 * //System.out.println("Test Data in onTestSkipped: " + testDataValue); }
	 */
	
	
	public ExtentReportListener() {
		
		eventListener = new WebEventlistener();
	}

	public void configureReport() {
		String reportFileLocation =   "extentreport"+File.separator+"index.html";
		String parentDirectoryPath = projectDirectory+File.separator+"extentreport"+File.separator;
		dtf = DateTimeFormatter.ofPattern("d MMM uuuu HH_mm_ss");
		now = LocalDateTime.now();
		//System.out.println(now.toLocalDate());
		//System.out.println(dtf.format(now));

		String folderName = "Automation Report "+now.toLocalDate();
		File parentDirectory = new File(parentDirectoryPath);
		File newFolder = new File(parentDirectory, folderName);

		if (!newFolder.exists()) {
			//System.out.println("i am in parent if");
			//System.out.println(reportFileLocation);
			boolean created = newFolder.mkdir();

			if (created) {
				//System.out.println("Folder created successfully.");
			} else {
				//System.out.println("Failed to create folder.");
			}
		} else {
			//System.out.println("Folder already exists.");
		}
		
		if(newFolder.exists()) {
		 reportFileLocation=projectDirectory+File.separator+"extentreport"+File.separator+folderName+File.separator+dtf.format(now)+" Report.html";
			
		}
		
		else {
	        System.out.println("Folder does not exist. Unable to set reportFileLocation.");
	       
	    }

		
		
		spark = new ExtentSparkReporter(reportFileLocation);
	//	spark = new ExtentSparkReporter("logs"+File.separator+"xyz.html");

		spark.config().setDocumentTitle("Vspace Automation Report");
		spark.config().setReportName("Automation Test execution report");
		spark.config().setTheme(Theme.STANDARD);
		//spark.config().setTimeStampFormat("");
		extent = new ExtentReports();
	    extent.setSystemInfo("os", System.getProperty("os.name"));
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

		Throwable t = new RuntimeException("A runtime exception");

		String er = null;
		String td = null;
		String[] arr = Result.getName().split("_");
		System.out.println(arr[0]);
		int row=Utilities.getRowNum(arr[0]);

		er= Utilities.readExcelCellData("data",row, 7);
		td= Utilities.readExcelCellData("data",row, 2);

		test = extent.createTest(Result.getName(),td);


		test.log(Status.FAIL, MarkupHelper.createLabel(er,ExtentColor.RED));
		//test.fail(MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+Result.getName()+".png", "provide info").build());
		test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+File.separator+"Screenshots"+File.separator+Result.getName()+".png").build());
		test.log(Status.FAIL, t);

		WebEventlistener.logger.info(Result.getName() + " : is Failed.");
		

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

		er= Utilities.readExcelCellData("data",row, 7);
		td= Utilities.readExcelCellData("data",row, 2);

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
		WebEventlistener.logger.info(Result.getName() + " : is Skipped.");
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
		//System.out.println(arr[0]);
		int row=Utilities.getRowNum(arr[0]);

		er= Utilities.readExcelCellData("data",row, 7);
		td= Utilities.readExcelCellData("data",row, 2);

		test = extent.createTest(Result.getName(),td);

		//test.log(Status.PASS, MarkupHelper.createLabel("Name of the passed testcase is : "+Result.getName(),ExtentColor.GREEN));
		test.assignCategory("Smoke Suite");
		test.log(Status.PASS, MarkupHelper.createLabel(er,ExtentColor.GREEN));
		WebEventlistener.logger.info(Result.getName() + " : is passed successfully.");

	}		

}			


