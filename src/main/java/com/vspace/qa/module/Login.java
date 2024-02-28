package com.vspace.qa.module;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vspace.qa.base.Base;

public class Login extends Base {

	@FindBy(id="email_id") WebElement emailField;
	@FindBy(id="password") WebElement passwordFiled;
	@FindBy(xpath = "//button[@type='submit']") WebElement signinButton;
	@FindBy(className = "directryBtn") WebElement gotoDirectrybtn;
	@FindBy(xpath = "//div[text()=' Login details are not valid!! ']") WebElement logincredmismatchmsg;



	public Login() {
		PageFactory.initElements(driver,this);

	}

	public void vldcredentialLogin(String email,String password) {
		//System.out.println(email);
		//System.out.println(password);
		emailField.clear();
		emailField.sendKeys(email);
		passwordFiled.clear();
		passwordFiled.sendKeys(password);
		signinButton.click();		

	}

	public Boolean chckLginVldCredential() {
		try { 
			gotoDirectrybtn.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }	
		

	}

	public Boolean chckLginInVldEmailCredential() {
		try { 
			logincredmismatchmsg.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }	
		

	}
	
	public Boolean chckLginInVldPasswdCredential() {
		
		  try { 
			  logincredmismatchmsg.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }		

	}

	public Boolean chckblankEmailCredential() {
		try { 
			emailField.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }	
		

	}

	public Boolean chckblankPasswdCredential() {
		try { 
			emailField.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }	
		
		

	}

	public Boolean chckblankemailPasswdCredential() {
		//System.out.println("Error message for Blank email or password : "+ emailField.getDomProperty("validationMessage"));
		//System.out.println("i am in lank mail method");
		try { 
			emailField.isDisplayed();
			  return true; 
			  }
		  catch(NoSuchElementException e) {
			  System.out.println(e);
			  return false; 
			  }	
		
	}


	public void invldcredentialLogin(String email,String password) {
		emailField.sendKeys(email);
		passwordFiled.sendKeys(password);
		signinButton.click();		

	}


}
