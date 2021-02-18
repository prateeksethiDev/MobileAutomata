package com.hello.driverInstance;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import com.hello.propertyFileReader.PropertyFileReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class HelloCommandBase extends HelloDriverInstance {

	/** Time out */
	private int timeout;

	/** Retry Count */
	private int retryCount;

	private SoftAssert softAssert = new SoftAssert();

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger("rootLogger");

	/** The Android driver. */
	public AndroidDriver<WebElement> androidDriver;

	/** The Android driver. */
	public IOSDriver<WebElement> iOSDriver;

	public HelloCommandBase(String Application) {
		super(Application);
		init();
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void init() {

		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
		logger.info(
				"Loaded the following properties" + " TimeOut :" + getTimeout() + " RetryCount :" + getRetryCount());
	}

	/**
	 * @param byLocator
	 * @return
	 */
	private WebElement findElement(By byLocator) {
		WebElement element = (new WebDriverWait(getDriver(), getTimeout()))
				.until(ExpectedConditions.presenceOfElementLocated(byLocator));
		return element;
	}
	
	/**
	 * @param byLocator
	 * @return
	 */
	private List<WebElement> findElements(By byLocator) {
		List<WebElement> listElement = (new WebDriverWait(getDriver(), getTimeout())).
				until(ExpectedConditions.numberOfElementsToBeMoreThan(byLocator, 1));
		return listElement;
	}
	
	

	/**
	 * Check element present.
	 *
	 * @param byLocator the by locator
	 * @return true, if successful
	 */
	public boolean verifyElementPresent(By byLocator) {

		try {
			findElement(byLocator);
			softAssert.assertEquals(findElement(byLocator).isDisplayed(), true,
					"The element " + byLocator + " " + "is displayed");
			logger.info("The element " + byLocator + " " + "is displayed");

			return true;
		} catch (Exception e) {
			softAssert.assertEquals(false, true, "Element" + byLocator + " " + "is not visible");
			softAssert.assertAll();
			logger.error("Element" + byLocator + " " + "is not visible");

			return false;
		}
	}

	/**
	 * @param byLocator
	 * @return true or false
	 */
	public boolean checkcondition(By byLocator) {
		boolean iselementPresent = false;
		try {
			iselementPresent = getDriver().findElement(byLocator).isDisplayed();
			iselementPresent = true;
		} catch (Exception e) {
			iselementPresent = false;
		}
		return iselementPresent;
	}

	/**
	 * Click on a web element.
	 * 
	 * @param byLocator the by locator
	 * 
	 */
	public void click(By byLocator) {

		try {
			WebElement element = findElement(byLocator);
			element.click();
			logger.info("Clicked on the object" + byLocator);
		} catch (Exception e) {
			logger.error(e);

		}
	}

	

	
	/**
	 * Type on a web element.
	 * 
	 * @param byLocator the by locator
	 * @param text      the text
	 */
	public void type(By byLocator, String text) {

		try {
			WebElement element = findElement(byLocator);
			element.sendKeys(text);
			logger.info("Typed the value " + text + " in to object " + byLocator);

		} catch (Exception e) {
			logger.error(e);

		}
	}

	/**
	 * Wait .
	 *
	 * @param x seconds to lock
	 */
	public void Wait(int x) {

		try {
			getDriver().manage().timeouts().implicitlyWait(x, TimeUnit.SECONDS);
			logger.info("Wait for " + x + "seconds");

		} catch (Exception e) {
			logger.error(e);
		}
	}

	
	/**
	 * @param byLocator Checks whether element is not displayed
	 */
	public void checkElementNotPresent(By byLocator) {
		boolean isElementNotPresent = true;
		try {
			isElementNotPresent = checkcondition(byLocator);
			softAssert.assertEquals(isElementNotPresent, false);
			logger.info("The element " + byLocator + " " + "is not displayed");

		} catch (Exception e) {
			softAssert.assertEquals(isElementNotPresent, true, "Element" + byLocator + " " + "is visible");
			softAssert.assertAll();
			logger.error("Element" + byLocator + " " + "is visible");

		}
	}

	/**
	 * closes the appliaction
	 */
	public void closeiOSApp() {
		try {
			getDriver().closeApp();

		} catch (Exception e) {

		}
	}

	public void closeSimulator() {
		try {
			getDriver().quit();

		} catch (Exception e) {

		}
	}
	
	public int countNumberOfElementsOnPage(By linkLocator) {
		return findElements(linkLocator).size();
	}
	public List<WebElement> listOfElementsOnPage(By linkLocator) {
		return findElements(linkLocator);
	}

	public List<WebElement> extractCurrentdatefromDatePicker(By locator) {
		
		List<WebElement> values=getDriver().findElements(locator);
		return values;
	}
}
