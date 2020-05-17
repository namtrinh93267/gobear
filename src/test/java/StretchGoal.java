import automationLibrary.actions.GeneralAction;
import automationLibrary.drivers.Driver;
import goBear.initiations.TestBase;
import goBear.initiations.TestConfigurations;
import goBear.pages.HomePage;
import org.testng.annotations.Test;

public class StretchGoal extends TestBase {
    @Test
    public void stretchGoal() {
        GeneralAction.navigateToPage(TestConfigurations.homePageUrl);
        Driver.startRecord(System.getProperty("user.dir") + "/recordVideos/");
        HomePage.selectInsuranceTab();
        HomePage.goToTravelSection();
        HomePage.goToTravelResultsPage();
        Driver.stopRecord();

        GeneralAction.verifyTest();
    }
}
