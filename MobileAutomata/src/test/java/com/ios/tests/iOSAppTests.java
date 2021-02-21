package com.ios.tests;

import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hello.driverInstance.HelloCommandBase;
import com.hello.iOSpages.HomePage;



public class iOSAppTests {

	private com.hello.driverInstance.HelloCommandBase caller;
	HomePage homePage=null;
	
	@BeforeClass
	public void init() {
		caller = new HelloCommandBase("UICatalog");
		homePage = new HomePage(caller);
	}
	
	@Test(priority=0)
	public void testCountLinksOnHomePage() throws MalformedURLException, InterruptedException {
		//Count how many list options are displayed
		System.out.println("Going to count number of links.....");
		int linkCount=homePage.countNumberOfLinksOnHomePage();
		System.out.println("Number of links on Page: "+linkCount );
		
		
		List<WebElement> list=homePage.getLinksOnHomePage();
		for(WebElement listElement:list) {
			System.out.println(listElement.getText());
		}
		//click on Alert views
		homePage.clickOnAlertViews();
		Thread.sleep(9000);
		homePage.clickOnBackLink();
	}
	
	@Test(priority=1)
	public void testDatePicker() throws InterruptedException {
	homePage.clickOnDatePicker();
	homePage.extractAndSetCurrentDate();
	}
	
	@Test(priority=3)
	public void testActionSheets() {
		homePage.clickOnActionSheets();
		//homePage.performActionOnActionSheetsOptions();
	}
}