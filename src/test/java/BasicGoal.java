import org.testng.annotations.Test;

import goBear.initiations.TestBase;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;

public class BasicGoal extends TestBase {
    @Test
    public void basicGoal() {
        HomePage homePage = new HomePage(this.driver);
        homePage.selectInsuranceTab();
        homePage.goToTravelSection();
        homePage.goToTravelResultsPage();

        TravelResultPage travelResultPage = new TravelResultPage(this.driver);
        travelResultPage.verifyAtLeastThreeCardsDisplayed();
        travelResultPage.verifyCategoriesAreFunctional();
       //Artifact.upload(new File(TestBase.videoPath), TestBase.videoPath);


//        TravelInsuranceSearch actualFilterTravelInsuranceSearch = travelResultPage.filterTravelInsurance(TestConfigurations.testDataFilter);
//        travelResultPage.verifyFilterTravelInsuranceSearch(actualFilterTravelInsuranceSearch);
//
//        TravelInsuranceSearch actualSortTravelInsuranceSearch = travelResultPage.sortTravelInsurance(TestConfigurations.testDataSort);
//        travelResultPage.verifySortTravelInsuranceSearch(actualSortTravelInsuranceSearch);
//
//        TravelInsuranceSearch actualDetailTravelInsuranceSearch = travelResultPage.detailTravelInsurance(TestConfigurations.testDataDetails);
//        travelResultPage.verifyDetailTravelInsuranceSearch(actualDetailTravelInsuranceSearch);
    }
}
