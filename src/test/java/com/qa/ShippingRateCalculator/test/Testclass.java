package com.qa.ShippingRateCalculator.test;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Testclass {

	private WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setup() {

		// Set up WebDriver using WebDriverManager
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	public void TestShippingQuote() {

		// launch application rate calculator using URL
		driver.get("https://www.pos.com.my/send/ratecalculator");
		driver.manage().window().maximize();

		// Verify Page Title
		Assert.assertEquals(driver.getTitle(), "Parcel Shipment Rate Calculator | Pos Malaysia");
		System.out.println("Title Verified");

		// Sending From Country as Malaysia
		// driver.findElement(By.xpath("//span[@class='font-bold']")).sendKeys("Malaysia");

		// Sending postal code
		driver.findElement(By.xpath("//input[@formcontrolname='postcodeFrom']")).sendKeys("35600");

		// Sending ToCountry as India
		WebElement ToCountry = driver.findElement(By.cssSelector("#mat-input-0"));
		ToCountry.clear();
		ToCountry.sendKeys("India");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement ToCountryOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[@title='India - IN']")));
		ToCountryOption.click();

		// Sending weight as 1
		driver.findElement(By.xpath("//input[@placeholder='eg. 0.1kg']")).sendKeys("1");

		// Click on Calculate button
		driver.findElement(By.xpath("//a[contains(text(),'Calculate')]")).click();

		// waiting for quotes to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Your Quote']")));

		// Fetching quotes count and verification for multiple shipment options
		int elementCount = driver.findElements(By.xpath("//div[contains(@class,'border-b border-gray-300')]")).size();
		if (elementCount > 1)
			System.out.println("Multiple shipment options available!");
		else
			System.out.println("Test failed: Multiple shipment options are not displayed.");
	}

	@AfterClass
	public void TearDown() {
		// Close the browser after test execution
		driver.quit();
	}

}
