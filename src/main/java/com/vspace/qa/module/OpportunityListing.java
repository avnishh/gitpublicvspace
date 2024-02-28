package com.vspace.qa.module;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vspace.qa.base.Base;

public class OpportunityListing extends Base{
@FindBy(className = "directryBtn") WebElement gotoDirectrybtn;
@FindBy(xpath = "//div[@class='col-sm-4 col-md-3']//li[3]") WebElement opppurtunityBtn;


public OpportunityListing() {
	PageFactory.initElements(driver,this);
	
}

public void opportunityList() {
	gotoDirectrybtn.click();
	opppurtunityBtn.click();
	
	
}

public String listCount() {
	List<WebElement> oppList = driver.findElements(By.xpath("//tbody/tr"));
	String data = driver.findElement(By.xpath("//tbody/tr/td[1]/a/strong")).getText();
	int opplistCount = oppList.size();
	System.out.println("The no. of items in oppurtunity is " + opplistCount);
	return data;
}
	



	
}
