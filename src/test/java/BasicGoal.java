import automationLibrary.actions.BaseAction;
import goBear.initiations.TestBase;
import goBear.initiations.TestConfigurations;
import goBear.objects.TravelInsuranceSearch;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;
import org.testng.annotations.Test;

public class BasicGoal extends TestBase {
    @Test
    public void basicGoal() {
        HomePage homePage = new HomePage(this.driver, this.softAssert);
        homePage.selectInsuranceTab();
        homePage.goToTravelSection();
        homePage.goToTravelResultsPage();

        TravelResultPage travelResultPage = new TravelResultPage(this.driver, this.softAssert);
        travelResultPage.verifyAtLeastThreeCardsDisplayed();
        travelResultPage.verifyCategoriesAreFunctional();

        TravelInsuranceSearch actualFilterTravelInsuranceSearch = travelResultPage.filterTravelInsurance(TestConfigurations.testDataFilter);
        travelResultPage.verifyFilterTravelInsuranceSearch(actualFilterTravelInsuranceSearch);

        TravelInsuranceSearch actualSortTravelInsuranceSearch = travelResultPage.sortTravelInsurance(TestConfigurations.testDataSort);
        travelResultPage.verifySortTravelInsuranceSearch(actualSortTravelInsuranceSearch);

        TravelInsuranceSearch actualDetailTravelInsuranceSearch = travelResultPage.detailTravelInsurance(TestConfigurations.testDataDetails);
        travelResultPage.verifyDetailTravelInsuranceSearch(actualDetailTravelInsuranceSearch);

        this.softAssert.assertAll();
    }
}
