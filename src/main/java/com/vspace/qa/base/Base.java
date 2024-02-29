package com.vspace.qa.base;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.*;

public class Base {

	public static WebDriver driver;
	public static HttpURLConnection connection;

	public static String urlToCheck = "http://192.168.100.59/vspace/login";

	public static void initialise() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver= new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		if (isWebsiteReachable()) {
			driver.get(urlToCheck);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		}
		else 
		{
			Assert.fail();
		}

	}

	public static Boolean  isWebsiteReachable() {

		try {
			URL url = new URL(urlToCheck);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
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


