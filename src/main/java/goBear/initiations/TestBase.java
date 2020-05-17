package goBear.initiations;

import automationLibrary.actions.GeneralAction;
import automationLibrary.drivers.Driver;
import automationLibrary.executions.Execution;
import automationLibrary.initiations.Configurations;
import automationLibrary.utils.ReportManager;
import org.apache.commons.lang3.SystemUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class TestBase {
    public boolean isMobileEmulation;
    String browser = System.getProperty("browser");
    String environment = System.getProperty("environment");
    String platform = System.getProperty("platform");

    @BeforeSuite
    public void beforeSuite() {
        String currentTime = GeneralAction.getCurrentTimeByTimezoneOffset(7, "yyyy-MM-dd-HH-mm-ss");
        String extentReportFilePath = Configurations.EXTENT_REPORT_PATH + currentTime + ".html";
        ReportManager.initExtentReport(extentReportFilePath, Configurations.EXTENT_REPORT_CONFIG_FILE);
        TestConfigurations.initTestData(environment);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        Execution.result = true;
        Execution.method = method;
        switch (platform) {
            case "web":
                isMobileEmulation = false;
                break;
            case "wap":
                isMobileEmulation = true;
                break;
        }

        System.out.println("");
        System.out.println("======" + "START RUNNING METHOD '" + method.getName() + "'======");
        Driver.initBrowser(browser, environment, isMobileEmulation);
        ReportManager.startTest(method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) {
        if(iTestResult.getStatus() == ITestResult.SUCCESS) {
            System.out.println("Result => PASSED");
        } else if(iTestResult.getStatus() == ITestResult.FAILURE) {
            System.out.println("Result => FAILED");
        } else if(iTestResult.getStatus() == ITestResult.SKIP) {
            System.out.println("Result => SKIP");
        }
        ReportManager.endTest();
        Driver.closeBrowser();
    }

    @AfterSuite
    public void afterSuite() {
        ReportManager.flushReport();
    }
}
