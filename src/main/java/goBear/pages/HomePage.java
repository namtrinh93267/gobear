package goBear.pages;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automationLibrary.actions.BaseAction;

public class HomePage extends BaseAction {
	
	Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());	

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
    	LOGGER.info("Select 'Insurance' tab");
        click(insuranceTab);
    }

    public void goToTravelSection() {
    	LOGGER.info("Select 'Insurance' tab");
        click(travelTab);
    }

    public void goToTravelResultsPage() {
    	LOGGER.info("Select 'Insurance' tab");
        click(showMyResultsButton);
        waitForElementPresent(noneDisplayLoadingStatusLocator());
    }
}
