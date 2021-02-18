package com.hello.driverInstance;

import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.hello.propertyFileReader.PropertyFileReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class HelloDriverInstance {
	protected static String host ;
	protected static int port;
	protected static String deviceName ;
	protected static String platform ;
	protected static int appTimeOut;
	protected static String automationName;
	protected static String platformVersion;
    
    public String getHost() {
		return host;
	}

	public void setHost(String host) {
		HelloDriverInstance.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		HelloDriverInstance.port = port;
	}
	
	public String getDeviceName(){
    	return deviceName;
    }
    
    public void setDeviceName(String deviceName){
    	HelloDriverInstance.deviceName = deviceName;
    }
    
    public String getPlatform(){
    	return platform;
    }
    
    public void setPlatfrom(String Platform){
    	HelloDriverInstance.platform = Platform;
    }
    
    public int getappTimeOut(){
    	return appTimeOut;
    }
    
    public void setappTimeOut(int timeOut){
    	HelloDriverInstance.appTimeOut = timeOut;
    }
    
    private void setPlatformVersion(String platformVersion) {
		HelloDriverInstance.platformVersion=platformVersion;
		
	}

    private String getPlatformVersion() {
    	return platformVersion;
    }
	private void setAutomationName(String automationName) {
		HelloDriverInstance.automationName=automationName;
		
	}
	
	 private String getAutomationName() {
	    	return automationName;
	    }

protected static AppiumDriver<WebElement> driver = null;
protected static PropertyFileReader handler=null;
    
	public static AppiumDriver<WebElement> getDriver() {
		return driver;
	}

	public void setDriver(AppiumDriver<WebElement> driver) {
		HelloDriverInstance.driver = driver;
	}
	
	public HelloDriverInstance(String Application) {
		
		try {
		init();	
		String remoteUrl = "http://" + getHost() + ":" + getPort() + "/wd/hub";
		if ("Android".equalsIgnoreCase(getPlatform())) {
			if(driver==null) {
		driver = (AppiumDriver<WebElement>)new AndroidDriver<WebElement>(new URL(remoteUrl), this.generateAndroidCapabilities(Application));
			}
		} else if ("iOS".equalsIgnoreCase(getPlatform())) {
			if(driver==null) {
		driver = (AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(remoteUrl), this.generateiOSCapabilities(Application));
			}
		} else {
		throw new Exception("Given platform is not implemented.");
		}
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	
	private void init() {
		if(handler==null) {
		handler = new PropertyFileReader("properties/Execution.properties");
		setHost(handler.getproperty("HOST_IP"));
		setPort(Integer.parseInt(handler.getproperty("HOST_PORT")));
		setDeviceName(handler.getproperty("DEVICE_NAME"));
		setPlatfrom(handler.getproperty("PLATFORM_NAME"));
		setappTimeOut(Integer.parseInt(handler.getproperty("APP_TIMEOUT")));
		setAutomationName(handler.getproperty("AUTOMATION_NAME"));
		setPlatformVersion(handler.getproperty("PLATFORM_VERSION"));
		}
	}
	
	

	/**
	 * @param application
	 * @return Android capabilities
	 */
	protected DesiredCapabilities generateAndroidCapabilities(String application) {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
        
		
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,getappTimeOut());
		return capabilities;
	}
	
	
	/**
	 * @param application
	 * @return iOS capabilities
	 */
	protected DesiredCapabilities generateiOSCapabilities(String application) {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		if (application.contains("UICatalog")) {
			//capabilities.setCapability("app", System.getProperty("user.dir") + "/apps/ios/UICatalog.app");
			capabilities.setCapability("bundleId", "com.example.apple-samplecode.UICatalog");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, getPlatform());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPlatformVersion());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getAutomationName());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());
			capabilities.setCapability("clearSystemFiles",true);
			
		} else {
			// To implement the Logger
		}
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, getappTimeOut());
		return capabilities;
	}
	
	public void tearDown(){
		driver.quit();
	}
}
