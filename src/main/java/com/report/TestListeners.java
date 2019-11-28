package com.report;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestListeners implements ITestListener{
	
	public static Hashtable<String, String> reportFiles = new Hashtable<String, String>();
	public static boolean flag = true;

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		String resultFolderpath = System.getProperty("user.dir")+File.separator+"test-output"+File.separator;
		ISuite testSuite = context.getSuite();
		XmlSuite xmlSuite = testSuite.getXmlSuite();
		List<XmlTest> xmlTest = xmlSuite.getTests();
		if(flag) {
			for(XmlTest test:xmlTest) {
				String reportName = test.getParameter("reportName");
				String filePath = resultFolderpath+reportName+".html";
				reportFiles.put(reportName.toLowerCase(), filePath);
			}
		}
		flag = false;
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
}
