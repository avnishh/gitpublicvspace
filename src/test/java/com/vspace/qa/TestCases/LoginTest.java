package com.vspace.qa.TestCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vspace.qa.base.Base;
import com.vspace.qa.module.Login;


//@Listeners(com.vspace.qa.TestCases.LoginTest.class)
public class LoginTest extends Base{

	Login login;
	String testDescription;
	String expectedResult;
	String urlNotReachable;

	/*
	 * @BeforeTest public void testurl() { initialise(); }
	 */

	@BeforeMethod 
	public void initialization() { 
		initialise();
		login = new Login();

	}


	@Test
	public void TC001_chckvldcredential_LoginTest() {
		// String methodName=new Exception().getStackTrace()[0].getMethodName();

		login.vldcredentialLogin("avnish.kumar@velocis.co.in", "Test@123");
		Boolean chckLoginvld = login.chckLginVldCredential();
		/*
		 * testDescription =
		 * "Verify that user login when passing valid email and password.";
		 * expectedResult = "User should log in successfully."; ITestResult result =
		 * Reporter.getCurrentTestResult(); result.setAttribute("td",testDescription);
		 * // key, value result.setAttribute("er",expectedResult);
		 */
		Assert.assertTrue(chckLoginvld);

	}

	@Test()
	public void TC002_chckInvldEmail_LoginTest() {

		login.vldcredentialLogin("avnish.umar@velocis.co.in", "Test@123");
		Boolean chckLogininvld = login.chckLginInVldEmailCredential();
		
		Assert.assertTrue(chckLogininvld);
	}

	@Test()
	public void TC003_chckInvldPasswd_LoginTest() {
		login.vldcredentialLogin("avnish.kumar@velocis.co.in", "Test@123");
		Boolean chckLogininvld = login.chckLginInVldPasswdCredential();
		
		//System.out.println((String)result.getAttribute("td"));


		Assert.assertTrue(chckLogininvld);


	}

	@Test(dependsOnMethods = "TC003_chckInvldPasswd_LoginTest")
	public void TC004_chckblankemail_LoginTest() {
		login.vldcredentialLogin("", "Test@123");
		Boolean chckblankemail = login.chckblankEmailCredential();
		
		Assert.assertTrue(chckblankemail);

	}



	@Test()
	public void TC005_chckblankpassword_LoginTest() {
		login.vldcredentialLogin("avnish.kumar@velocis.co.in", "");
		Boolean chckblankemail = login.chckblankPasswdCredential();
		
		Assert.assertTrue(chckblankemail);
	}

	@Test()
	public void TC006_chckblankemailPassws_LoginTest() {
		login.vldcredentialLogin("", "");
		Boolean chckblankemail = login.chckblankemailPasswdCredential();
		
		Assert.assertTrue(chckblankemail);
	}


	@AfterMethod

	public void tearDown() {
		tear();


	}

}
