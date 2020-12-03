package goBear.initiations;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import automationLibrary.actions.BaseAction;
import automationLibrary.drivers.DriverManager;
import automationLibrary.drivers.DriverManagerFactory;
import automationLibrary.drivers.DriverType;

public class TestBase {
	String runType = "agent";
    String environment = "production";
    boolean isMobileEmulation = Boolean.parseBoolean(System.getProperty("isMobileEmulation"));

    public DriverManager driverManager;
    public WebDriver driver;
    public SoftAssert softAssert;
    public static String videoPath;

    @BeforeSuite
    public void beforeSuite() {
        TestConfigurations.initTestData(environment);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("");
        System.out.println("======" + "START RUNNING METHOD '" + method.getName() + "'======");
        driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        if(runType.equals("agent")) {
        	driver = driverManager.getDriver(isMobileEmulation);
        } else {
        	//driver = getDriver();
        }
        softAssert = new SoftAssert();
        driver.get(TestConfigurations.homePageUrl);

        //Start video recorder
        String videoFolder = "D://xampp/htdocs/videos/";
        String videoName = BaseAction.getCurrentTimeByTimezoneOffset(7, "dd-MM-yyyy-HH-mm-ss");
        //videoPath = videoFolder + videoName + ".mp4";
        //driverManager.startRecord(videoFolder, videoName);
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) {
        //driverManager.stopRecord();	
        driverManager.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {

    }
}
