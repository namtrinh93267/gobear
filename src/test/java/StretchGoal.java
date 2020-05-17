import automationLibrary.actions.GeneralAction;
import automationLibrary.drivers.Driver;
import automationLibrary.utils.ReportManager;
import com.relevantcodes.extentreports.LogStatus;
import goBear.initiations.TestBase;
import goBear.initiations.TestConfigurations;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;
import org.testng.annotations.Test;

public class StretchGoal extends TestBase {
    @Test
    public void stretchGoal() {
        GeneralAction.navigateToPage(TestConfigurations.homePageUrl);
        String videoFolder = System.getProperty("user.dir") + "/recordVideos/";
        String videoName = GeneralAction.getCurrentTimeByTimezoneOffset(7, "dd-MM-yyyy-HH-mm-ss");
        Driver.startRecord(videoFolder, videoName);
        HomePage.selectInsuranceTab();
        HomePage.goToTravelSection();
        HomePage.goToTravelResultsPage();
        TravelResultPage.verifyLeftSideMenuIsFunctional();
        Driver.stopRecord();
        GeneralAction.verifyTest();
    }
}
