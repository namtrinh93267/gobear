package automationLibrary.utils;

import automationLibrary.initiations.Configurations;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;

public class ReportManager {
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    public static void initExtentReport(String reportFileName, String reportConfig) {
        extentReports = new ExtentReports(reportFileName, true);
        extentReports.loadConfig(new File(reportConfig));
    }

    public static void startTest(String text) {
        extentTest = extentReports.startTest(text);
    }

    public static void endTest() {
         extentReports.endTest(extentTest);
    }

    public static void addReportLog(LogStatus logStatus, String text) {
        System.out.println(text);
        extentTest.log(logStatus, text);
    }

    public static String addScreencast(String screencastPath) {
        return extentTest.addScreencast(screencastPath);
    }

    public static void flushReport() {
        extentReports.flush();
    }
}
