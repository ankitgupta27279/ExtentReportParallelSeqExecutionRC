package com.rippon;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Class1Test extends BaseTest {

	
	@Test(priority=1)
	public void meth2() {
		Assert.assertTrue(true);
	}
	
	@Test(priority=2)
	public void meth3() {
		Assert.assertTrue(false);
	}
	
	@Test(priority=3)
	public void meth4() {
		Assert.assertTrue(true);
	}
	
	@Test(priority=4)
	public void skipTest(){
		throw new SkipException("Skipping - This is not ready for testing ");
	}
	
}
