package goBear.pages;

import automationLibrary.actions.GeneralAction;
import automationLibrary.executions.Execution;
import automationLibrary.utils.ReportManager;
import com.relevantcodes.extentreports.LogStatus;
import goBear.initiations.Constant;
import goBear.objects.Details;
import goBear.objects.Filter;
import goBear.objects.Sort;
import goBear.objects.TravelInsuranceSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TravelResultPage {

    //Interactive elements
    public static WebElement promotionFilterRadioButton(String promotion) {
        return GeneralAction.getElement(By.xpath("//div[@data-filter-name='" + promotion + "']"));
    }

    public static WebElement insurerCheckbox(String insurer) {
        return GeneralAction.getElement(By.xpath("//div[@data-filter-name='" + insurer + "']"));
    }

    public static WebElement seeMoreLink() {
        return GeneralAction.getElement(By.id("collapseSeemoreBtn"));
    }

    public static WebElement minSlider(String sliderName) {
        return GeneralAction.getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//div[contains(@class,'min-slider-handle')]"));
    }

    public static WebElement maxSlider(String sliderName) {
        return GeneralAction.getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//div[contains(@class,'max-slider-handle')]"));
    }

    public static WebElement minMoney(String sliderName) {
        return GeneralAction.getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//b[@data-min-value]"));
    }

    public static WebElement maxMoney(String sliderName) {
        return GeneralAction.getElement(By.xpath("//div[@data-type='Number'][contains(.,'" + sliderName + "')]//b[@data-max-value]"));
    }

    public static WebElement sortRadioButton(String sortOption) {
        return GeneralAction.getElement(By.xpath("//div[@data-gb-name='sort-option']/label[contains(.,'" + sortOption + "')]"));
    }

    public static WebElement policyTypeRadioButton(String policyType) {
        return GeneralAction.getElement(By.xpath("//div[@data-gb-trip-types='" + policyType + "']"));
    }

    public static WebElement whoGoingRadioButton(String option) {
        return GeneralAction.getElement(By.xpath("//div[@name='traveller-type']//label[contains(.,'" + option + "')]"));
    }

    public static WebElement destinationSelect() {
        return GeneralAction.getElement(By.xpath("//div[contains(@class,'field-select')]"));
    }

    public static WebElement destinationOption(String destination) {
        return GeneralAction.getElement(By.xpath("//div[contains(@class,'field-select')]//ul/li/a/span[.='" + destination + "']"));
    }

    public static WebElement startTravelDate() {
        return GeneralAction.getElement(By.xpath("//input[@name='dates-startdate']"));
    }

    public static WebElement endTravelDate() {
        return GeneralAction.getElement(By.xpath("//input[@name='dates-enddate']"));
    }

    public static WebElement datePickerSwitch() {
        return GeneralAction.getElement(By.xpath("//div[contains(@class,'datepicker')][not(contains(@style,'display'))]//th[@class='datepicker-switch']"));
    }

    public static WebElement datePickerDay(String day) {
        return GeneralAction.getElement(By.xpath("//div[@class='datepicker-days']//td[.='" + day + "']"));
    }

    public static WebElement datePickerMonth(String month) {
        return GeneralAction.getElement(By.xpath("//div[@class='datepicker-months']/table/tbody/tr/td/span[contains(@class,'month')][.='" + month + "']"));
    }

    public static WebElement datePickerYear(String year) {
        return GeneralAction.getElement(By.xpath("//div[@class='datepicker-years']/table/tbody/tr/td/span[contains(@class,'year')][.='" + year + "']"));
    }

    public static void verifyAtLeastThreeCardsDisplayed() {
        ReportManager.addReportLog(LogStatus.INFO, "Verify at least 3 cards are being displayed");
        List<WebElement> cardList = GeneralAction.getElements(By.xpath("//div[@class='grid-row']/div[contains(@class,'card-full')]"));
        int cardAmount = cardList.size();
        if (cardAmount < 3) {
            Execution.setTestFail("Actual card amount is " + cardAmount + ", expected card amount is at least 3");
        }
    }

    public static void verifyCategoriesAreFunctional() {
        ReportManager.addReportLog(LogStatus.INFO, "Verify categories are functional");
        GeneralAction.verifyElementExists(By.xpath("//div[contains(@class,'filter-detail')]"));
        GeneralAction.verifyElementExists(By.xpath("//div[contains(@class,'sort-detail')]"));
        GeneralAction.verifyElementExists(By.xpath("//div[contains(@class,'edit-detail')]"));
    }

    public static void selectDateFromDatePicker(String date) {
        String[] splitDate = date.split("-");
        String day = splitDate[0];
        String month = splitDate[1];
        String year = splitDate[2];
        GeneralAction.click(datePickerSwitch());
        GeneralAction.click(datePickerSwitch());
        GeneralAction.click(datePickerYear(year));
        GeneralAction.click(datePickerMonth(month));
        GeneralAction.clickAndWait(datePickerDay(day));
    }

    public static TravelInsuranceSearch filterTravelInsurance(Filter filter) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        ReportManager.addReportLog(LogStatus.INFO, "Filter promotion '" + filter.getPromotion() + "'");
        GeneralAction.click(promotionFilterRadioButton(filter.getPromotion()));
        travelInsuranceSearch.setPromotion(filter.getPromotion());

        List<String> insurerList = filter.getInsurerList();
        if (insurerList.size() > 0) {
            for (int i = 0; i < insurerList.size(); i++) {
                ReportManager.addReportLog(LogStatus.INFO, "Filter insurer '" + insurerList.get(i) + "'");
                GeneralAction.clickAndWait(insurerCheckbox(insurerList.get(i)));
            }
        }
        travelInsuranceSearch.setInsurerList(filter.getInsurerList());

        if (filter.getPersonalLossOffset() != null || filter.getTripTerminationOffset() != null || filter.getTripCancellationOffset() != null || filter.getMedicalExpensesOffset() != null || filter.getPersonalAccidentOffset() != null) {
            if (GeneralAction.isElementPresent(By.xpath("//div[@class='see-more collapsed']"))) {
                ReportManager.addReportLog(LogStatus.INFO, "Click 'SEE MORE' link");
                GeneralAction.click(seeMoreLink());
            }

            if (filter.getPersonalAccidentOffset() != null) {
                String[] splitPersonalAccidentOffset = filter.getPersonalAccidentOffset().split(",");
                GeneralAction.changeRangeSelector(minSlider(Constant.Filter.PERSONAL_ACCIDENT), Integer.parseInt(splitPersonalAccidentOffset[0]));
                GeneralAction.changeRangeSelector(maxSlider(Constant.Filter.PERSONAL_ACCIDENT), Integer.parseInt(splitPersonalAccidentOffset[1]));
                String minMoney = minMoney(Constant.Filter.PERSONAL_ACCIDENT).getText().substring(1);
                String maxMoney = maxMoney(Constant.Filter.PERSONAL_ACCIDENT).getText().substring(1);
                travelInsuranceSearch.setPersonalAccidentMoney(minMoney + "-" + maxMoney);
            }

            if (filter.getMedicalExpensesOffset() != null) {
                String[] splitMedicalExpensesOffset = filter.getMedicalExpensesOffset().split(",");
                GeneralAction.changeRangeSelector(minSlider(Constant.Filter.MEDICAL_EXPENSES), Integer.parseInt(splitMedicalExpensesOffset[0]));
                GeneralAction.changeRangeSelector(maxSlider(Constant.Filter.MEDICAL_EXPENSES), Integer.parseInt(splitMedicalExpensesOffset[1]));
            }

            if (filter.getTripCancellationOffset() != null) {
                String[] splitTripCancellationOffset = filter.getTripCancellationOffset().split(",");
                GeneralAction.changeRangeSelector(minSlider(Constant.Filter.TRIP_CANCELLATION), Integer.parseInt(splitTripCancellationOffset[0]));
                GeneralAction.changeRangeSelector(maxSlider(Constant.Filter.TRIP_CANCELLATION), Integer.parseInt(splitTripCancellationOffset[1]));
            }

            if (filter.getTripTerminationOffset() != null) {
                String[] splitTripTerminationOffset = filter.getTripTerminationOffset().split(",");
                GeneralAction.changeRangeSelector(minSlider(Constant.Filter.TRIP_TERMINATION), Integer.parseInt(splitTripTerminationOffset[0]));
                GeneralAction.changeRangeSelector(maxSlider(Constant.Filter.TRIP_TERMINATION), Integer.parseInt(splitTripTerminationOffset[1]));
            }

            if (filter.getPersonalLossOffset() != null) {
                String[] splitPersonalLossOffset = filter.getPersonalLossOffset().split("-");
                GeneralAction.changeRangeSelector(minSlider(Constant.Filter.LOSS_PERSONAL), Integer.parseInt(splitPersonalLossOffset[0]));
                GeneralAction.changeRangeSelector(maxSlider(Constant.Filter.LOSS_PERSONAL), Integer.parseInt(splitPersonalLossOffset[1]));
            }
        }
        return travelInsuranceSearch;
    }

    public static TravelInsuranceSearch sortTravelInsurance(Sort sort) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        ReportManager.addReportLog(LogStatus.INFO, "Sort '" + sort.getSortOption() + "'");
        GeneralAction.click(sortRadioButton(sort.getSortOption()));
        travelInsuranceSearch.setSortOption(sort.getSortOption());
        return travelInsuranceSearch;
    }

    public static TravelInsuranceSearch detailTravelInsurance(Details details) {
        TravelInsuranceSearch travelInsuranceSearch = new TravelInsuranceSearch();
        travelInsuranceSearch.setUseDetail(details.isUseDetails());
        ReportManager.addReportLog(LogStatus.INFO, "Select policy type '" + details.getPolicyType() + "'");
        GeneralAction.clickAndWait(policyTypeRadioButton(details.getPolicyType()));
        travelInsuranceSearch.setPolicyType(details.getPolicyType());
        ReportManager.addReportLog(LogStatus.INFO, "Select who's going '" + details.getWhoGoing() + "'");
        GeneralAction.clickAndWait(whoGoingRadioButton(details.getWhoGoing()));
        travelInsuranceSearch.setWhoGoing(details.getWhoGoing());
        ReportManager.addReportLog(LogStatus.INFO, "Select destination '" + details.getDestination() + "'");
        GeneralAction.click(destinationSelect());
        GeneralAction.clickAndWait(destinationOption(details.getDestination()));
        travelInsuranceSearch.setDestination(details.getDestination());
        ReportManager.addReportLog(LogStatus.INFO, "Select travel start date '" + details.getStartDate() + "'");
        GeneralAction.click(startTravelDate());
        selectDateFromDatePicker(details.getStartDate());
        travelInsuranceSearch.setStartDate(details.getStartDate());
        ReportManager.addReportLog(LogStatus.INFO, "Select travel end date '" + details.getEndDate() + "'");
        GeneralAction.click(endTravelDate());
        selectDateFromDatePicker(details.getEndDate());
        travelInsuranceSearch.setEndDate(details.getEndDate());
        return travelInsuranceSearch;
    }

    public static void verifyFilterTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        ReportManager.addReportLog(LogStatus.INFO, "Verify cards contain insurer in insurers list '" + travelInsuranceSearch.getInsurerList() + "'");
        for(int i = 0; i < travelInsuranceSearch.getInsurerList().size(); i++) {
            List<WebElement> cardList = GeneralAction.getElements(By.xpath(cardXpath));
            for(int j = 1; j <= cardList.size(); j++) {
                String cardBrandXpath = cardXpath + "[" + j + "]//div[@class='card-brand']/h4";
                String brand = GeneralAction.getElement(By.xpath(cardBrandXpath)).getText().trim();
                if(!travelInsuranceSearch.getInsurerList().contains(brand)) {
                    Execution.setTestFail("Card list does not contain card with brand '" + brand + "' at card index " + j);
                }
            }
        }

        ReportManager.addReportLog(LogStatus.INFO, "Verify personal accident money in range '" + travelInsuranceSearch.getPersonalAccidentMoney() + "'");
        String[] splitMoneyRange = travelInsuranceSearch.getPersonalAccidentMoney().split("-");
        int minMoney = Integer.parseInt(splitMoneyRange[0].replace(",", ""));
        int maxMoney = Integer.parseInt(splitMoneyRange[1].replace(",", ""));
        List<WebElement> cardList = GeneralAction.getElements(By.xpath(cardXpath));
        for(int i = 1; i <= cardList.size(); i++) {
            String cardMoneyXpath = cardXpath + "[" + i + "]//div[contains(@class,'col-xs')][contains(.,'Personal Accident')]//strong";
            int money = Integer.parseInt(GeneralAction.getElement(By.xpath(cardMoneyXpath)).getText().substring(1).replace(",","").trim());
            if(!(minMoney <= money && money <= maxMoney)) {
                Execution.setTestFail("Money is not in range at card index " + i);
            }
        }
    }

    public static void verifySortTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        switch (travelInsuranceSearch.getSortOption()) {
            case "Price: High to low":
                ReportManager.addReportLog(LogStatus.INFO, "Verify sort price from high to low");
                List<WebElement> cardList = GeneralAction.getElements(By.xpath(cardXpath));
                for(int i = 1; i <= cardList.size(); i++) {
                    if(i <= cardList.size() - 1) {
                        String currentCardPriceXpath = cardXpath + "[" + i + "]//div[@class='policy-price']";
                        String nextCardPriceXpath = cardXpath + "[" + (i + 1) + "]//div[@class='policy-price']";
                        int currentPrice = Integer.parseInt(GeneralAction.getElement(By.xpath(currentCardPriceXpath)).getAttribute("premium").trim());
                        int nextPrice = Integer.parseInt(GeneralAction.getElement(By.xpath(nextCardPriceXpath)).getAttribute("premium").trim());
                        if(currentPrice < nextPrice) {
                            Execution.setTestFail("Price in not sort from high to low at card index " + i);
                        }
                    }
                }
                break;
        }
    }

    public static void verifyDetailTravelInsuranceSearch(TravelInsuranceSearch travelInsuranceSearch) {
        String cardXpath = "//div[@class='grid-row']/div[contains(@class,'card-full')]";
        switch (travelInsuranceSearch.getPolicyType()) {
            case "single":
                ReportManager.addReportLog(LogStatus.INFO, "Verify single trip cards displayed");
                List<WebElement> cardList = GeneralAction.getElements(By.xpath(cardXpath));
                for(int i = 1; i <= cardList.size(); i++) {
                    String singleTripCardXpath = cardXpath + "[" + i + "]//sub[@class='unit']";
                    String unit = GeneralAction.getElement(By.xpath(singleTripCardXpath)).getText().trim();
                    if(!unit.equals("(Including taxes)")) {
                        Execution.setTestFail("Card is not single trip at card index " + i);
                    }
                }
                break;
        }
    }

    public static void verifyLeftSideMenuIsFunctional() {
        ReportManager.addReportLog(LogStatus.INFO, "Verify left side menu is functional");
        GeneralAction.verifyElementExists(By.xpath("//div[contains(@class,'sidebar-wrapper')]"));
    }
}
