package com.vspace.qa.TestCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vspace.qa.base.Base;
import com.vspace.qa.module.Login;

public class testing extends Base {
	
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
		//login = new Login();

	}


	@Test
	public void TC001_chckvldcredential_LoginTest() {
		// String methodName=new Exception().getStackTrace()[0].getMethodName();
driver.findElement(By.xpath("//textarea[@id='APjFqb']")).sendKeys("hello");
		

	}

	@Test()
	public void TC002_chckInvldEmail_LoginTest() {
		driver.findElement(By.xpath("//textarea[@id='APjFqb']")).sendKeys("hello");
		driver.findElement(By.xpath("//div[@class='FPdoLc lJ9FBc']//input[@name='btnK']")).click();
		
	}






	@AfterMethod

	public void tearDown() {
		tear();

}}
