package com.vspace.qa.base;

import java.net.HttpURLConnection;
import com.vspace.qa.utility.WebEventlistener;
import java.net.URL;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.Assert;
import jdk.internal.org.jline.utils.Log;

import org.testng.*;

public class Base {

	public static WebDriver driver;
	public static HttpURLConnection connection;
	//public static Logger logger;
	//public static String urlToCheck = "http://192.168.100.59/vspace/login";
	public static String urlToCheck = "http://www.google.com";

	public static WebEventlistener listener;

	public static void initialise() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriver original = new ChromeDriver(options);
		  listener = new WebEventlistener();
	        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(listener); //Pass listener to constructor
	         driver = decorator.decorate(original);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		if (isWebsiteReachable()) {
			driver.get(urlToCheck);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		}
		else 
		{
			Assert.fail();
		}

	}
	
	public Base() {
		
		//logger = LogManager.getLogger(Base.class);
		

	}

	public static Boolean  isWebsiteReachable() {

		try {
			URL url = new URL(urlToCheck);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
			//logger.info("hi i am logger");
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (200<=responseCode && responseCode<=399) {
				//	System.out.println("URL is reachable.");
				return true;


			} else {

				System.out.println("URL is not reachable. Response code: " + responseCode);
				driver.quit();
				return false;
				//Assert.fail();

			}


		} catch (Exception e) {
			System.out.println("Error while checking URL reachability: " + e.getMessage());
			driver.quit();
			return false;
			//Assert.fail();
		} 
		finally {
			connection.disconnect();
		}
	}

	public static void tear() {
		driver.quit();

	}

}


