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
        ChromeOptions options = new ChromeOptions();

        if(isMobileEmulation) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
    }
}