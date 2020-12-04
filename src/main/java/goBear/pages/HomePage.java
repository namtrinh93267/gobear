package goBear.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qaprosoft.carina.core.foundation.utils.Messager;

import automationLibrary.actions.BaseAction;

public class HomePage extends BaseAction {

    public HomePage(WebDriver driver) {
        super(driver);
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
    	Messager.INROMATION.info("Select 'Insurance' tab");
        click(insuranceTab);
    }

    public void goToTravelSection() {
    	Messager.INROMATION.info("Go to 'Travel' section");
        click(travelTab);
    }

    public void goToTravelResultsPage() {
    	Messager.INROMATION.info("Go to Travel result page");
        click(showMyResultsButton);
        waitForElementPresent(noneDisplayLoadingStatusLocator());
    }
}
