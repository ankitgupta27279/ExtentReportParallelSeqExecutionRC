package com.rippon;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.report.ExtentReportManager;
import com.report.ExtentTestManager;
import com.report.TestListeners;

public class BaseTest {

	@BeforeTest(alwaysRun = true)
	@Parameters({"deviceType", "platformName", "platformVersion", "deviceName", "remoteURL", "browser", "udid"})
	public void initSetUp(ITestContext testContext, String deviceType, String platformName, String platformVersion, String deviceName, String remoteURL, 
			@Optional String browser, @Optional String udid) {
		initDriver(deviceType, platformName, platformVersion, deviceName, remoteURL, browser, udid);
		initReportTest(testContext);
	}
	
	public void initDriver(String deviceType, String platformName, String platformVersion, String deviceName, String remoteURL, @Optional String browser, 
			@Optional String udid) {
		
	}
	
	public void initReportTest(ITestContext testContext) {
		XmlTest xmlTest = testContext.getCurrentXmlTest();
		Map<String, String> testParameters = xmlTest.getAllParameters();
		String reportName = xmlTest.getParameter("reportName");
		if(TestListeners.reportFiles.keySet().contains(reportName.toLowerCase())) {
			ExtentReports _extent;
			_extent = new ExtentReports(TestListeners.reportFiles.get(reportName.toLowerCase()), true);
			_extent.loadConfig(new File(System.getProperty("user.dir")+File.separator+"runtime"+File.separator+"extent_config.xml"));
			_extent.addSystemInfo("Device Type", testParameters.get("deviceType"));
			_extent.addSystemInfo("Platform Name", testParameters.get("platformName"));
			_extent.addSystemInfo("Platform Version", testParameters.get("platformVersion"));
			_extent.addSystemInfo("Device Name", testParameters.get("deviceName"));
			_extent.addSystemInfo("Browser", testParameters.get("browser"));
			ExtentReportManager.setExtentReport(_extent);
		}else {
			System.out.println("file not available");
		}
	}
	
	@AfterTest
	public void tearDown(ITestContext testContext) {
		ExtentReportManager.getExtentReport().endTest(ExtentTestManager.getExtentTest());
		ExtentReportManager.getExtentReport().flush();
	}
	
	@AfterSuite
	public void endAllReports() {
		ExtentReportManager.extentReportManager.remove();
	}
	
	@BeforeMethod
	public void setExtentReport(Method method) {
		ExtentTestManager.setExtentTest(ExtentReportManager.getExtentReport().startTest(method.getName()));
		System.out.println("****"+method.getName());
	}
	
	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getExtentTest().log(LogStatus.FAIL, "Test case failed due to "+result.getThrowable());
			//screenshot code
			// ExtentTestManager.getExtentTest().log(LogStatus.FAIL, ExtentTestManager.getExtentTest().addScreenCapture(""));
		}else if(result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getExtentTest().log(LogStatus.SKIP, "Test case skipped is "+result.getName());
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getExtentTest().log(LogStatus.PASS, "TC Passed");
		}
	}
}
