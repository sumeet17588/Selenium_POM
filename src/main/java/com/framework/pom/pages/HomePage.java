package com.framework.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.pom.base.Base;
import com.framework.pom.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage extends Base {
	
	@FindBy(xpath = Constants.lnk_InputForms)
	public WebElement lnk_InputForms;
	
	public HomePage(WebDriver driver,ExtentTest test){
		super(driver,test);
	}
	
	public void gotoPage(String expTitle){
		driver.get(Constants.url);
		if(driver.getTitle().equals(expTitle)) {
			test.log(LogStatus.PASS, "Successfully navigated to page having expected title " + expTitle + " and actual title is " + driver.getTitle());
			takeScreenShot();
		}else {
			reportFailure("Failed to navigate to page having expected title " + expTitle + " and actual title is " + driver.getTitle());
			takeScreenShot();
		}		
	}
	
	public Object navigateToInputForms() {
		clickObject(Constants.lnk_InputForms);
		boolean status = clickObject(Constants.lnk_InputForms_SimpleFormDemo);
		
		if(status) {
			test.log(LogStatus.PASS, "Successfully navigated to Simple form demo page");
			SimpleFormDemo pSimpleFormDemo = new SimpleFormDemo(driver, test);
			PageFactory.initElements(driver, pSimpleFormDemo);
			takeScreenShot();
			return pSimpleFormDemo;
		}else {
			test.log(LogStatus.FAIL	, "Failed to navigate to Simple form demo page");
			HomePage pHome = new HomePage(driver, test);
			PageFactory.initElements(driver, pHome);
			takeScreenShot();
			return pHome;
		}
		
		
	}

}
