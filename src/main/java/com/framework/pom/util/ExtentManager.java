package com.framework.pom.util;

//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	public static String screenshotFolderPath;
	public static String reportPath = Constants.reportPath;
	
	public static ExtentReports getInstance() {
		
		
		if (extent == null) {
			
			String fileName="Report.html";
			Date d = new Date();
			String folderName=d.toString().replace(":", "_");
			new File(reportPath+folderName+"//Screenshots").mkdirs();
			reportPath=reportPath+folderName+"//";
    		screenshotFolderPath=reportPath+"screenshots//";
 
			extent = new ExtentReports(reportPath+fileName, true, DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(Constants.extentReport_ConfigFile));
			// optional
			extent.addSystemInfo("Selenium Version", "2.53.0").addSystemInfo(
					"Environment", "QA");
		}
		return extent;
	}
}
