package goBear.pages;

import automationLibrary.actions.GeneralAction;
import automationLibrary.executions.Execution;
import automationLibrary.utils.ReportManager;
import com.relevantcodes.extentreports.LogStatus;
import goBear.objects.TravelInsuranceSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {
    //Interactive elements
    public static WebElement insuranceTab() {
        return GeneralAction.getElement(By.xpath("//a[@aria-controls='Insurance'][@role='tab']"));
    }

    public static WebElement travelTab() {
        return GeneralAction.getElement(By.xpath("//a[@aria-controls='Travel'][@role='tab']"));
    }

    public static WebElement tripSelect() {
        return GeneralAction.getElement(By.xpath("//select[@name='travel-form-trip-type']"));
    }

    public static WebElement travellerSelect() {
        return GeneralAction.getElement(By.xpath("//select[@name='travel-form-traveller']"));
    }

    public static WebElement countrySelect() {
        return GeneralAction.getElement(By.xpath("//select[@name='travel-form-country']"));
    }

    public static WebElement showMyResultsButton() {
        return GeneralAction.getElement(By.xpath("//div[@id='Insurance']//button[.='Show my results']"));
    }

    //Wait locators
    public static By noneDisplayLoadingStatusLocator() {
        return By.xpath("//div[@data-gb-name='loading-status'][@style='display: none;']");
    }

    public static void selectInsuranceTab() {
        ReportManager.addReportLog(LogStatus.INFO, "Select 'Insurance' tab");
        GeneralAction.click(insuranceTab());
    }

    public static void goToTravelSection() {
        ReportManager.addReportLog(LogStatus.INFO, "Go to Travel section");
        GeneralAction.click(travelTab());
    }

    public static void goToTravelResultsPage() {
        ReportManager.addReportLog(LogStatus.INFO, "Go to Travel results page");
        GeneralAction.click(showMyResultsButton());
        GeneralAction.waitForElementPresent(noneDisplayLoadingStatusLocator());
    }
}
