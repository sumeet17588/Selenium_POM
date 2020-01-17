package com.framework.pom.util;

public class Constants {
	
	//Paths
	public static final String reportPath = System.getProperty("user.dir")+"\\Reports\\";
	public static final String extentReport_ConfigFile = System.getProperty("user.dir")+"\\resources\\ReportsConfig.xml";
	public static final String dataSheet_FilePath = System.getProperty("user.dir")+"\\Data\\MasterData.xlsx";
	public static final String propertiesFile = System.getProperty("user.dir") + "\\resources\\constants.properties";
	public static final String firefoxDriver = System.getProperty("user.dir") + "\\resources\\geckodriver.exe";
	public static final String chromeDriver = System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
	
	//URLs
	public static final String url = "https://www.seleniumeasy.com/test/";
	
	//Locators
	public static final String lnk_InputForms = "//ul[@id='treemenu']/li/ul/li[1]/a";
	public static final String lnk_InputForms_SimpleFormDemo = "//ul[@id='treemenu']/li/ul/li[1]/ul/li[1]/a";
	//Simple Input Form Demo
	public static final String txt_1stNum = "//*[@id='sum1']";
	public static final String txt_2ndNum = "//*[@id='sum2']";
	public static final String btn_GetTotal = "//form[@id='gettotal']/button";
	public static final String result_Total = "//span[@id='displayvalue']";
}
