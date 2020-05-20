package goBear.pages;

import automationLibrary.actions.BaseAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class HomePage extends BaseAction {

    public HomePage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
        PageFactory.initElements(driver, this);
    }

    //Interactive elements
    @FindBy(xpath="//a[@aria-controls='Insurance'][@role='tab']")
    WebElement insuranceTab;

    @FindBy(xpath="//a[@aria-controls='Travel'][@role='tab']")
    WebElement travelTab;

    @FindBy(xpath="//div[@id='Insurance']//button[.='Show my results']")
    WebElement showMyResultsButton;

    //Wait locators
    public By noneDisplayLoadingStatusLocator() {
        return By.xpath("//div[@data-gb-name='loading-status'][@style='display: none;']");
    }

    public void selectInsuranceTab() {
        Reporter.log("Select 'Insurance' tab");
        click(insuranceTab);
    }

    public void goToTravelSection() {
        Reporter.log("Go to Travel section");
        click(travelTab);
    }

    public void goToTravelResultsPage() {
        Reporter.log("Go to Travel results page");
        click(showMyResultsButton);
        waitForElementPresent(noneDisplayLoadingStatusLocator());
    }
}
