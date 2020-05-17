import automationLibrary.actions.GeneralAction;
import goBear.initiations.TestBase;
import goBear.initiations.TestConfigurations;
import goBear.objects.TravelInsuranceSearch;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;
import org.testng.annotations.Test;

public class BasicGoal extends TestBase {
    @Test
    public void basicGoal() {
        GeneralAction.navigateToPage(TestConfigurations.homePageUrl);
        HomePage.selectInsuranceTab();
        HomePage.goToTravelSection();
        HomePage.goToTravelResultsPage();
        TravelResultPage.verifyAtLeastThreeCardsDisplayed();
        TravelResultPage.verifyCategoriesAreFunctional();

        TravelInsuranceSearch actualFilterTravelInsuranceSearch = TravelResultPage.filterTravelInsurance(TestConfigurations.testDataFilter);
        TravelResultPage.verifyFilterTravelInsuranceSearch(actualFilterTravelInsuranceSearch);

        TravelInsuranceSearch actualSortTravelInsuranceSearch = TravelResultPage.sortTravelInsurance(TestConfigurations.testDataSort);
        TravelResultPage.verifySortTravelInsuranceSearch(actualSortTravelInsuranceSearch);

        TravelInsuranceSearch actualDetailTravelInsuranceSearch = TravelResultPage.detailTravelInsurance(TestConfigurations.testDataDetails);
        TravelResultPage.verifyDetailTravelInsuranceSearch(actualDetailTravelInsuranceSearch);

        GeneralAction.verifyTest();
    }
}
