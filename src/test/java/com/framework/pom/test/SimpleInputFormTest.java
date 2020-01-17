package com.framework.pom.test;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.framework.pom.base.Base;
import com.framework.pom.pages.HomePage;
import com.framework.pom.pages.SimpleFormDemo;
import com.framework.pom.util.DataUtil;
import com.relevantcodes.extentreports.LogStatus;

public class SimpleInputFormTest extends Base {
	
	String testCaseName = "SimpleInputFormTest";
	
	@Test(dataProvider="getData")
	public void SimpleInputForm(Hashtable<String,String> data) {
		test=extent.startTest("Simple Input form demo Test");
		
		if(!DataUtil.isTestExecutable(xls, testCaseName) ||  data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as Rnumode is N");
			throw new SkipException("Skipping the test as Rnumode is N");
		}
		
		initBrowser(data.get("Browser"));
		
		HomePage pHome = new HomePage(driver, test);
		PageFactory.initElements(driver, pHome);
		pHome.gotoPage(data.get("PageTitle"));
		pHome.navigateToInputForms();
		
		SimpleFormDemo pSimpleFormDemo = new SimpleFormDemo(driver, test);
		PageFactory.initElements(driver, pSimpleFormDemo);
		String total = pSimpleFormDemo.TwoInputFoelds(data.get("Num1"), data.get("Num2"));
		int Sum = 0;
		try {
			Sum = Integer.parseInt(data.get("Num1")) + Integer.parseInt(data.get("Num2"));
		}catch(NumberFormatException n) {
			reportFailure("Invalid number format." + n.getMessage());
			takeScreenShot();
		}
		
		if(Sum == Integer.parseInt(total)) {
			test.log(LogStatus.PASS, "Successfully validate the sum of 2 numbers");	
			takeScreenShot();
		}else {
			reportFailure("Validation on sum of 2 numbers failed. Actual - " + total + ", Expected - " + Sum);
			takeScreenShot();
		}
		
		
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
