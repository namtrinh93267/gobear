import org.testng.annotations.Test;

import com.qaprosoft.carina.core.foundation.AbstractTest;

import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;

public class StretchGoal extends AbstractTest {
    @Test
    public void stretchGoal() {
        HomePage homePage = new HomePage(getDriver());
        homePage.selectInsuranceTab();
        homePage.goToTravelSection();
        homePage.goToTravelResultsPage();

        TravelResultPage travelResultPage = new TravelResultPage(getDriver());
        travelResultPage.verifyLeftSideMenuIsFunctional();
    }
}