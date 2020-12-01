package automationLibrary.drivers;

import automationLibrary.initiations.Configurations;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class ChromeDriverManager extends DriverManager {
    @Override
    public void createDriver(boolean isMobileEmulation) {
        String chromedriverPath = "";
        
        ChromeOptions options = new ChromeOptions();
    	options.addArguments("--disable-web-security");
    	options.addArguments("--disable-popup-blocking");
		options.addArguments("--headless");
		options.addArguments("--window-size=1600,900");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
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
        System.setProperty("webdriver.chrome.driver", chromedriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        if(isMobileEmulation) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
    }
}