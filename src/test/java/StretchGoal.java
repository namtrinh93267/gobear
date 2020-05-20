import automationLibrary.actions.BaseAction;
import goBear.initiations.TestBase;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;
import org.testng.annotations.Test;

public class StretchGoal extends TestBase {
    @Test
    public void stretchGoal() {
        HomePage homePage = new HomePage(this.driver, this.softAssert);
        homePage.selectInsuranceTab();
        homePage.goToTravelSection();
        homePage.goToTravelResultsPage();

        TravelResultPage travelResultPage = new TravelResultPage(this.driver, this.softAssert);
        travelResultPage.verifyLeftSideMenuIsFunctional();

        this.softAssert.assertAll();
    }
}