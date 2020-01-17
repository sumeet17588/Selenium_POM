package com.framework.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.framework.pom.base.Base;
import com.framework.pom.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;

public class SimpleFormDemo extends Base {
	
	@FindBy(xpath = Constants.result_Total)
	public WebElement total;
	
	public SimpleFormDemo(WebDriver driver,ExtentTest test){
		super(driver,test);
	}
	
	public String TwoInputFoelds(String a, String b) {
		setValue(Constants.txt_1stNum, a);
		setValue(Constants.txt_2ndNum, b);
		clickObject(Constants.btn_GetTotal);
		return total.getText();
	}

}
