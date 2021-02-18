package com.hello.iOSpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.hello.driverInstance.HelloCommandBase;

public class HomePage {
HelloCommandBase caller=null;
	
	public HomePage(HelloCommandBase caller) {
		this.caller=caller;
	}
	
	private By linksOnHomePage=By.xpath("//XCUIElementTypeStaticText");
	private By alertViewLink=By.id("Alert Views");
	private By alertlinkBack=By.xpath("//XCUIElementTypeButton[@name='UICatalog']");
	private By datePickerLink=By.id("Date Picker");
	private By datePickerWheel=By.xpath("//XCUIElementTypePickerWheel");
	private By actionSheetLink =By.id("Action Sheets");
	private By actionSheetsOtherLink=By.id("Other");
	private By actionSheetOptions=By.id("Safe Choice");
	
	public int countNumberOfLinksOnHomePage() {
		int linkCount=caller.countNumberOfElementsOnPage(linksOnHomePage);
		return linkCount;
	}
	
	public List<WebElement> getLinksOnHomePage() {
		return caller.listOfElementsOnPage(linksOnHomePage);
	}

	public void clickOnAlertViews() {
		caller.click(alertViewLink);		
	}
	
	public void clickOnBackLink() {
		caller.click(alertlinkBack);
	}
	
	public void clickOnDatePicker() {
		caller.click(datePickerLink);
	}
	
	public void extractAndSetCurrentDate() throws InterruptedException {
		List<WebElement> list=caller.extractCurrentdatefromDatePicker(datePickerWheel);
		
		for(int index=0;index<list.size();index++) {
			System.out.println(list.get(index).getAttribute("value"));
		}
		list.get(0).sendKeys("Tue Feb 16");
		list.get(0).sendKeys(Keys.TAB);
		
		list.get(1).sendKeys("10");
		list.get(1).sendKeys(Keys.TAB);
		
		list.get(2).sendKeys("09");
		list.get(2).sendKeys(Keys.TAB);
		
		list.get(3).sendKeys("AM");
		
		Thread.sleep(9000);
		caller.click(alertlinkBack);
	}

	public void clickOnActionSheets() {
		caller.click(actionSheetLink);
		caller.verifyElementPresent(actionSheetsOtherLink);
	}
	
	public void performActionOnActionSheetsOptions() {
		caller.click(actionSheetsOtherLink);
		caller.click(actionSheetOptions);
		caller.checkElementNotPresent(actionSheetOptions);
	}
	
	
}
