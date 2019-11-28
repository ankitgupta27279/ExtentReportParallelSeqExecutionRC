package com.report;

import com.relevantcodes.extentreports.ExtentTest;

public class ExtentTestManager {

	public static ThreadLocal<ExtentTest> extentTestManager = new ThreadLocal<ExtentTest>();
	
	public static void setExtentTest(ExtentTest rep) {
		extentTestManager.set(rep);
	}
	
	public static ExtentTest getExtentTest() {
		return extentTestManager.get();
	}
}
