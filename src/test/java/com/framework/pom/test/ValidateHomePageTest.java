package com.framework.pom.test;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.framework.pom.base.Base;
import com.framework.pom.pages.HomePage;
import com.framework.pom.util.DataUtil;
import com.relevantcodes.extentreports.LogStatus;

public class ValidateHomePageTest extends Base {
	
	String testCaseName = "ValidateHomePageTest";
	
	@Test(dataProvider="getData")
	public void validateHomePage(Hashtable<String,String> data) {
		
		test=extent.startTest("Home Page Test");
		
		if(!DataUtil.isTestExecutable(xls, testCaseName) ||  data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as Rnumode is N");
			throw new SkipException("Skipping the test as Rnumode is N");
		}
		
		test.log(LogStatus.INFO, "Starting validate home page test");
		initBrowser(data.get("Browser"));
		
		HomePage pHome = new HomePage(driver, test);
		PageFactory.initElements(driver, pHome);
		pHome.gotoPage(data.get("PageTitle"));
	}
	
	@AfterMethod
	public void quit(){
		if(extent!=null){
			extent.endTest(test);
			extent.flush();
		}
		if(driver!=null)
			driver.quit();
	}
	
	
	@DataProvider
	public Object[][] getData(){
		return DataUtil.getData(xls, testCaseName);
	}

}
