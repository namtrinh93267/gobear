package automationLibrary.drivers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import automationLibrary.initiations.Configurations;
import io.github.bonigarcia.wdm.WebDriverManager;


public class ChromeDriverManager extends DriverManager {
    @Override
    public void createDriver(boolean isMobileEmulation) {
    	//WebDriverManager.chromedriver().setup();
        String chromedriverPath = "";
        
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);
//		options.addArguments("--disable-web-security");
//		options.addArguments("--disable-popup-blocking");
//		options.addArguments("--window-size=1600,900");
//		options.addArguments("disable-infobars");
//		options.addArguments("--disable-extensions");
//		options.addArguments("--disable-gpu");
//		options.addArguments("--disable-dev-shm-usage");
//		options.addArguments("--no-sandbox");
		 
    	Map<String, Object> prefs = new HashMap<String, Object>();
    	prefs.put("credentials_enable_service", false);
    	prefs.put("profile.password_manager_enabled", false);
    	prefs.put("default_content_setting.popups", 1);
    	prefs.put("profile.default_content_setting_values.notifications", 2);
    	options.setExperimentalOption("prefs", prefs);
        
        if(SystemUtils.IS_OS_LINUX) {
            File file64 = new File(Configurations.CHROMEDRIVER_LINUX64_FILE_PATH);
         	File file32 = new File(Configurations.CHROMEDRIVER_LINUX32_FILE_PATH);
    		file64.setExecutable(true);
    		file32.setExecutable(true);
    		if(SystemUtils.OS_ARCH.contains("64")) {
    			chromedriverPath = Configurations.CHROMEDRIVER_LINUX64_FILE_PATH;
    		} else {
    			chromedriverPath = Configurations.CHROMEDRIVER_LINUX32_FILE_PATH;
    		}
    	} else if(SystemUtils.IS_OS_MAC_OSX) {
            chromedriverPath = Configurations.CHROMEDRIVER_MACOS_FILE_PATH;
        } else if(SystemUtils.IS_OS_WINDOWS) {
            chromedriverPath = Configurations.CHROMEDRIVER_WINDOWS_FILE_PATH;
        }
       //System.setProperty("webdriver.chrome.driver", chromedriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        if(isMobileEmulation) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new RemoteWebDriver(capabilities);
//        try {
//			driver = new RemoteWebDriver(new URL("http://demo:demo@10.90.96.7:80/selenoid/wd/hub"), capabilities);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}