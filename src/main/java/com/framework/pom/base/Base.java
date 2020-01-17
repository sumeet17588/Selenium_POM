package com.framework.pom.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.pom.util.Constants;
import com.framework.pom.util.ExtentManager;
import com.framework.pom.util.Xls_Reader;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Base {
	
	public WebDriver driver;
	public ExtentTest test;
	public ExtentReports extent = ExtentManager.getInstance();
	public Xls_Reader xls = new Xls_Reader(Constants.dataSheet_FilePath);
	
	//Default constructor
	public Base() {
			
		}
	
	//Constructor
	public Base(WebDriver driver, ExtentTest test) {
		this.driver=driver;
		this.test=test;
	}
	
	//Capture screen shot
	public void takeScreenShot(){
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		String filePath=ExtentManager.screenshotFolderPath+screenshotFile;
		// store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			Files.copy(scrFile, new File(filePath));
			//FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.log(LogStatus.INFO,test.addScreenCapture(filePath));
	}
	
	//Read properties file
	public String readPropertiesFile(String key) {
		Properties prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(Constants.propertiesFile);
			prop.load(fs);
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		String value = prop.getProperty(key);
		if(value == null) {
			test.log(LogStatus.FAIL, "No value found against key in constants.properties " + key);
			Assert.fail("No value found against key in constants.properties " + key);
		}
		return value;		
	}
	
	//Report failure with log
	public void reportFailure(String failureMessage){
		test.log(LogStatus.FAIL, failureMessage);
		takeScreenShot();
		Assert.fail(failureMessage);
	}
	
	//Static wait
	public void wait(int timeToWaitInSec){
		try {
			Thread.sleep(timeToWaitInSec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Wait to load page if any java script is running
	public void waitForPageToLoad() {
		wait(1);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String state = (String)js.executeScript("return document.readyState");
		
		while(!state.equals("complete")){
			wait(2);
			state = (String)js.executeScript("return document.readyState");
		}
	}
	
	//Open browser
	public void initBrowser(String browserName){
		
		test.log(LogStatus.INFO, "Opening browser - "+ browserName);
		if(browserName.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", Constants.firefoxDriver);
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			driver= new FirefoxDriver();
		}
		else if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", Constants.chromeDriver);
			driver= new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Opened browser successfully - "+ browserName);

	}
	
	//Object exist
	public boolean objExist(String xpath) {
		int objCount = driver.findElements(By.xpath(xpath)).size();
		if(objCount == 0) {
			reportFailure("Object not found having xpath - " + xpath);
		}
		else {
			return true;
		}
		return false;
	}
	
	//Set value in a text field
	public boolean setValue(String xpath, String value) {
		
		if(!objExist(xpath)) {
			reportFailure("Object not found having xpath - " + xpath);
		}
		
		WebDriverWait wait = new WebDriverWait(driver, 180);
		try {
			WebElement obj = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			obj.sendKeys(value);
			return true;
		}catch(Throwable e) {
			reportFailure("Object state is not ready having xpath - " + xpath);
		}
		return false;
	}
	
	//Click on an object
	public boolean clickObject(String xpath) {
			
			if(!objExist(xpath)) {
				reportFailure("Object not found having xpath - " + xpath);
			}
			
			WebDriverWait wait = new WebDriverWait(driver, 180);
			try {
				WebElement obj = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				obj.click();
				return true;
			}catch(Throwable e) {
				reportFailure("Object state is not ready having xpath - " + xpath + "--" + e.getMessage());
			}
			return false;
		}
	
	//single selection by visible text from select tag drop down
	public boolean selectValueFromDropList(String xpath, String visibleText) {
			
			if(!objExist(xpath)) {
				reportFailure("Object not found having xpath - " + xpath);
			}
			
			WebDriverWait wait = new WebDriverWait(driver, 180);
			try {
				WebElement list = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				Select select = new Select(list);
				select.selectByVisibleText(visibleText);
				if(select.getFirstSelectedOption().getText().equals(visibleText))
					return true;
				else
					return false;
				
			}catch(Throwable e) {
				reportFailure("Object state is not ready having xpath - " + xpath + "--" + e.getMessage());
			}
			
			return false;
		}
	
	//single selection by index from select tag drop down. index starts from 0
	public boolean selectValueFromDropList(String xpath, int index) {
			
			if(!objExist(xpath)) {
				reportFailure("Object not found having xpath - " + xpath);
			}
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			try {
				WebElement list = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				Select select = new Select(list);
				select.selectByIndex(index);
				if(!select.getFirstSelectedOption().getText().equals(""))
					return true;
				else
					return false;
				
			}catch(Throwable e) {
				reportFailure("Object state is not ready having xpath - " + xpath + "--" + e.getMessage());
			}
			
			return false;
		}
	
}
