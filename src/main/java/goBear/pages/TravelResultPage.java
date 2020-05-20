package goBear.pages;

import automationLibrary.actions.BaseAction;
import goBear.initiations.Constant;
import goBear.objects.Details;
import goBear.objects.Filter;
import goBear.objects.Sort;
import goBear.objects.TravelInsuranceSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TravelResultPage extends BaseAction {

    public TravelResultPage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
        PageFactory.initElements(driver, this);
    }

    //Interactive elements
    public WebElement promotionFilterRadioButton(String promotion) {
        return getElement(By.xpath("//div[@data-filter-name='" + promotion + "']"));
    }

    public WebElement insurerCheckbox(String insurer) {
        return getElement(By.xpath("//div[@data-filter-name='" + insurer + "']"));
    }

    @FindBy(id="collapseSeemoreBtn")
    WebElement seeMoreLink;

    public WebElement minSlider(String sliderName) {
        return getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//div[contains(@class,'min-slider-handle')]"));
    }

    public WebElement maxSlider(String sliderName) {
        return getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//div[contains(@class,'max-slider-handle')]"));
    }

    public WebElement minMoney(String sliderName) {
        return getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//b[@data-min-value]"));
    }

    public WebElement maxMoney(String sliderName) {
        return getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//b[@data-max-value]"));
    }

    public WebElement sortRadioButton(String sortOption) {
        return getElement(By.xpath("//div[@data-gb-name='sort-option']/label[contains(.,'" + sortOption + "')]"));
    }

    public WebElement policyTypeRadioButton(String policyType) {
        return getElement(By.xpath("//div[@data-gb-trip-types='" + policyType + "']"));
    }

    public WebElement whoGoingRadioButton(String option) {
        return getElement(By.xpath("//div[@name='traveller-type']//label[contains(.,'" + option + "')]"));
    }

    @FindBy(xpath="//div[contains(@class,'field-select')]")
    WebElement destinationSelect;

    public WebElement destinationOption(String destination) {
        return getElement(By.xpath("//div[contains(@class,'field-select')]//ul/li/a/span[.='" + destination + "']"));
    }

    @FindBy(xpath="//input[@name='dates-startdate']")
    WebElement startTravelDate;

    @FindBy(xpath="//input[@name='dates-enddate']")
    WebElement endTravelDate;

    @FindBy(xpath="//div[contains(@class,'datepicker')][not(contains(@style,'display'))]//th[@class='datepicker-switch']")
    WebElement datePickerSwitch;

    public WebElement datePickerDay(String day) {
        return getElement(By.xpath("//div[@class='datepicker-days']//td[.='" + day + "']"));
    }

    public WebElement datePickerMonth(String month) {
        return getElement(By.xpath("//div[@class='datepicker-months']/table/tbody/tr/td/span[contains(@class,'month')][.='" + month + "']"));
    }

    public WebElement datePickerYear(String year) {
        return getElement(By.xpath("//div[@class='datepicker-years']/table/tbody/tr/td/span[contains(@class,'year')][.='" + year + "']"));
    }

    public void verifyAtLeastThreeCardsDisplayed() {
        Reporter.log("Verify at least 3 cards are being displayed");
        List<WebElement> cardList = getElements(By.xpath("//div[@class='grid-row']/div[contains(@class,'card-full')]"));
        int cardAmount = cardList.size();
        if (cardAmount < 3) {
            softAssert.assertTrue(false, "Actual card amount is " + cardAmount + ", expected card amount is at least 3");
        }
    }

    public void verifyCategoriesAreFunctional() {
        Reporter.log("Verify categories are functional");
        String filterSectionXpath = "//div[contains(@class,'filter-detail')]";
        String sortSectionXpath = "//div[contains(@class,'sort-detail')]";
        String editSectionXpath = "//div[contains(@class,'edit-detail')]";
        softAssert.assertTrue(isElementPresent(By.xpath(filterSectionXpath)), "Element " + filterSectionXpath + " does not exist");
        softAssert.assertTrue(isElementPresent(By.xpath(sortSectionXpath)), "Element " + sortSectionXpath + " does not exist");
        softAssert.assertTrue(isElementPresent(By.xpath(editSectionXpath)), "Element " + editSectionXpath + " does not exist");
    }

    public void selectDateFromDatePicker(String date) {
        String[] splitDate = date.split("-");
        String day = splitDate[0];
        String month = splitDate[1];
        String year = splitDate[2];
        click(datePickerSwitch);
        click(datePickerSwitch);
        click(datePickerYear(year));
        click(datePickerMonth(month));
        clickAndWait(datePickerDay(day));
    }

    public TravelInsuranceSearch filterTravelInsurance(Filter filter) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        Reporter.log("Filter promotion '" + filter.getPromotion() + "'");
        click(promotionFilterRadioButton(filter.getPromotion()));
        travelInsuranceSearch.setPromotion(filter.getPromotion());

        List<String> insurerList = filter.getInsurerList();
        if (insurerList.size() > 0) {
            for (int i = 0; i < insurerList.size(); i++) {
                Reporter.log("Filter insurer '" + insurerList.get(i) + "'");
                clickAndWait(insurerCheckbox(insurerList.get(i)));
            }
        }
        travelInsuranceSearch.setInsurerList(filter.getInsurerList());

        if (filter.getPersonalLossOffset() != null || filter.getTripTerminationOffset() != null || filter.getTripCancellationOffset() != null || filter.getMedicalExpensesOffset() != null || filter.getPersonalAccidentOffset() != null) {
            if (isElementPresent(By.xpath("//div[@class='see-more collapsed']"))) {
                Reporter.log("Click 'SEE MORE' link");
                click(seeMoreLink);
            }

            if (filter.getPersonalAccidentOffset() != null) {
                String[] splitPersonalAccidentOffset = filter.getPersonalAccidentOffset().split(",");
                changeRangeSelector(minSlider(Constant.Filter.PERSONAL_ACCIDENT), Integer.parseInt(splitPersonalAccidentOffset[0]));
                changeRangeSelector(maxSlider(Constant.Filter.PERSONAL_ACCIDENT), Integer.parseInt(splitPersonalAccidentOffset[1]));
                String minMoney = minMoney(Constant.Filter.PERSONAL_ACCIDENT).getText().substring(1);
                String maxMoney = maxMoney(Constant.Filter.PERSONAL_ACCIDENT).getText().substring(1);
                travelInsuranceSearch.setPersonalAccidentMoney(minMoney + "-" + maxMoney);
            }

            if (filter.getMedicalExpensesOffset() != null) {
                String[] splitMedicalExpensesOffset = filter.getMedicalExpensesOffset().split(",");
                changeRangeSelector(minSlider(Constant.Filter.MEDICAL_EXPENSES), Integer.parseInt(splitMedicalExpensesOffset[0]));
                changeRangeSelector(maxSlider(Constant.Filter.MEDICAL_EXPENSES), Integer.parseInt(splitMedicalExpensesOffset[1]));
            }

            if (filter.getTripCancellationOffset() != null) {
                String[] splitTripCancellationOffset = filter.getTripCancellationOffset().split(",");
                changeRangeSelector(minSlider(Constant.Filter.TRIP_CANCELLATION), Integer.parseInt(splitTripCancellationOffset[0]));
                changeRangeSelector(maxSlider(Constant.Filter.TRIP_CANCELLATION), Integer.parseInt(splitTripCancellationOffset[1]));
            }

            if (filter.getTripTerminationOffset() != null) {
                String[] splitTripTerminationOffset = filter.getTripTerminationOffset().split(",");
                changeRangeSelector(minSlider(Constant.Filter.TRIP_TERMINATION), Integer.parseInt(splitTripTerminationOffset[0]));
                changeRangeSelector(maxSlider(Constant.Filter.TRIP_TERMINATION), Integer.parseInt(splitTripTerminationOffset[1]));
            }

            if (filter.getPersonalLossOffset() != null) {
                String[] splitPersonalLossOffset = filter.getPersonalLossOffset().split("-");
                changeRangeSelector(minSlider(Constant.Filter.LOSS_PERSONAL), Integer.parseInt(splitPersonalLossOffset[0]));
                changeRangeSelector(maxSlider(Constant.Filter.LOSS_PERSONAL), Integer.parseInt(splitPersonalLossOffset[1]));
            }
        }
        return travelInsuranceSearch;
    }

    public TravelInsuranceSearch sortTravelInsurance(Sort sort) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        Reporter.log("Sort '" + sort.getSortOption() + "'");
        click(sortRadioButton(sort.getSortOption()));
        travelInsuranceSearch.setSortOption(sort.getSortOption());
        return travelInsuranceSearch;
    }

    public TravelInsuranceSearch detailTravelInsurance(Details details) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        travelInsuranceSearch.setUseDetail(details.isUseDetails());
        Reporter.log("Select policy type '" + details.getPolicyType() + "'");
        clickAndWait(policyTypeRadioButton(details.getPolicyType()));
        travelInsuranceSearch.setPolicyType(details.getPolicyType());
        Reporter.log("Select who's going '" + details.getWhoGoing() + "'");
        clickAndWait(whoGoingRadioButton(details.getWhoGoing()));
        travelInsuranceSearch.setWhoGoing(details.getWhoGoing());
        Reporter.log("Select destination '" + details.getDestination() + "'");
        click(destinationSelect);
        clickAndWait(destinationOption(details.getDestination()));
        travelInsuranceSearch.setDestination(details.getDestination());
        Reporter.log("Select travel start date '" + details.getStartDate() + "'");
        click(startTravelDate);
        selectDateFromDatePicker(details.getStartDate());
        travelInsuranceSearch.setStartDate(details.getStartDate());
        Reporter.log("Select travel end date '" + details.getEndDate() + "'");
        click(endTravelDate);
        selectDateFromDatePicker(details.getEndDate());
        travelInsuranceSearch.setEndDate(details.getEndDate());
        return travelInsuranceSearch;
    }

    public void verifyFilterTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        Reporter.log("Verify cards contain insurer in insurers list '" + travelInsuranceSearch.getInsurerList() + "'");
        for(int i = 0; i < travelInsuranceSearch.getInsurerList().size(); i++) {
            List<WebElement> cardList = getElements(By.xpath(cardXpath));
            for(int j = 1; j <= cardList.size(); j++) {
                String cardBrandXpath = cardXpath + "[" + j + "]//div[@class='card-brand']/h4";
                String brand = getElement(By.xpath(cardBrandXpath)).getText().trim();
                if(!travelInsuranceSearch.getInsurerList().contains(brand)) {
                    softAssert.assertTrue(false, "Card list does not contain card with brand '" + brand + "' at card index " + j);
                }
            }
        }

        Reporter.log("Verify personal accident money in range '" + travelInsuranceSearch.getPersonalAccidentMoney() + "'");
        String[] splitMoneyRange = travelInsuranceSearch.getPersonalAccidentMoney().split("-");
        int minMoney = Integer.parseInt(splitMoneyRange[0].replace(",", ""));
        int maxMoney = Integer.parseInt(splitMoneyRange[1].replace(",", ""));
        List<WebElement> cardList = getElements(By.xpath(cardXpath));
        for(int i = 1; i <= cardList.size(); i++) {
            String cardMoneyXpath = cardXpath + "[" + i + "]//div[contains(@class,'col-xs')][contains(.,'Personal Accident')]//strong";
            int money = Integer.parseInt(getElement(By.xpath(cardMoneyXpath)).getText().substring(1).replace(",","").trim());
            if(!(minMoney <= money && money <= maxMoney)) {
                softAssert.assertTrue(false, "Money is not in range at card index " + i);
            }
        }
    }

    public void verifySortTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        switch (travelInsuranceSearch.getSortOption()) {
            case "Price: High to low":
                Reporter.log("Verify sort price from high to low");
                List<WebElement> cardList = getElements(By.xpath(cardXpath));
                for(int i = 1; i <= cardList.size(); i++) {
                    if(i <= cardList.size() - 1) {
                        String currentCardPriceXpath = cardXpath + "[" + i + "]//div[@class='policy-price']";
                        String nextCardPriceXpath = cardXpath + "[" + (i + 1) + "]//div[@class='policy-price']";
                        int currentPrice = Integer.parseInt(getElement(By.xpath(currentCardPriceXpath)).getAttribute("premium").trim());
                        int nextPrice = Integer.parseInt(getElement(By.xpath(nextCardPriceXpath)).getAttribute("premium").trim());
                        if(currentPrice < nextPrice) {
                            softAssert.assertTrue(false, "Price in not sort from high to low at card index " + i);
                        }
                    }
                }
                break;
        }
    }

    public void verifyDetailTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        switch (travelInsuranceSearch.getPolicyType()) {
            case "single":
                Reporter.log("Verify single trip cards displayed");
                List<WebElement> cardList = getElements(By.xpath(cardXpath));
                for(int i = 1; i <= cardList.size(); i++) {
                    String singleTripCardXpath = cardXpath + "[" + i + "]//sub[@class='unit']";
                    String unit = getElement(By.xpath(singleTripCardXpath)).getText().trim();
                    if(!unit.equals("(Including taxes)")) {
                        softAssert.assertTrue(false, "Card is not single trip at card index " + i);
                    }
                }
                break;
        }
    }

    public void verifyLeftSideMenuIsFunctional() {
        Reporter.log("Verify left side menu is functional");
        softAssert.assertTrue(isElementPresent(By.xpath("//div[contains(@class,'sidebar-wrapper')]")));
    }
}