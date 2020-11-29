package goBear.initiations;

import automationLibrary.actions.BaseAction;
import automationLibrary.drivers.DriverManager;
import automationLibrary.drivers.DriverManagerFactory;
import automationLibrary.drivers.DriverType;
import automationLibrary.utils.VideoRecorder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class TestBase {
    String environment = "production";
    boolean isMobileEmulation = Boolean.parseBoolean(System.getProperty("isMobileEmulation"));

    public DriverManager driverManager;
    public WebDriver driver;
    public SoftAssert softAssert;

    @BeforeSuite
    public void beforeSuite() {
        TestConfigurations.initTestData(environment);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("");
        System.out.println("======" + "START RUNNING METHOD '" + method.getName() + "'======");
        driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        driver = driverManager.getDriver(isMobileEmulation);
        softAssert = new SoftAssert();
        driver.get(TestConfigurations.homePageUrl);

        //Start video recorder
        String videoFolder = System.getProperty("user.dir") + "/recordVideos/";
        String videoName = BaseAction.getCurrentTimeByTimezoneOffset(7, "dd-MM-yyyy-HH-mm-ss");
        driverManager.startRecord(videoFolder, videoName);
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) {
        driverManager.stopRecord();
        driverManager.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {

    }
}
