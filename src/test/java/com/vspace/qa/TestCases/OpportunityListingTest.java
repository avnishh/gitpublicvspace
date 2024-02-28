package com.vspace.qa.TestCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vspace.qa.base.Base;
import com.vspace.qa.module.Login;
import com.vspace.qa.module.OpportunityListing;
import com.vspace.qa.utility.ExtentReportListener;

public class OpportunityListingTest extends Base{

	Login login;
	OpportunityListing oppList;
	String testDescription;
	String expectedResult;

		
	@BeforeMethod 
	public void initialization() {
		initialise();
		login = new Login();
		oppList = new OpportunityListing();
		login.vldcredentialLogin("avnish.kumar@velocis.co.in", "Test@123");
		Boolean chckLoginvld = login.chckLginVldCredential();
		String testData = "Login functionality not working";
       // ExtentReportListener.setTestData(testData);
		Assert.assertTrue(chckLoginvld);
		

	}


	@Test
	public void TC007_opportunityBtn_clcksuccess() {
		//login.vldcredentialLogin("avnish.kumar@velocis.co.in", "Test@123");
		oppList.opportunityList();
		
	}

	@Test
	public void TC008_oppListcountChck() {
		//login.vldcredentialLogin("avnish.kumar@velocis.co.in", "Test@123");
		oppList.opportunityList();
		String data = oppList.listCount();
		System.out.println(data);
		
	}




	@AfterMethod
	public void tearDown() {
		tear();
			}
	
	}




