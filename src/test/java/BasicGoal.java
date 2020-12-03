import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.agent.core.registrar.ArtifactReference;

import automationLibrary.utils.TestListener;
import goBear.initiations.TestBase;
import goBear.pages.HomePage;
import goBear.pages.TravelResultPage;

@Listeners(TestListener.class)
public class BasicGoal extends TestBase {
	Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @TestLabel(name = "web", value = {"reporting-service:v1.0", "reporting-service:v1.1"})
    @Test
    public void basicGoal() {
    	LOGGER.info("start test 2");
        HomePage homePage = new HomePage(this.driver);
        LOGGER.info("Select Insurance tab");
        homePage.selectInsuranceTab();
        LOGGER.info("Go to travel section");
        homePage.goToTravelSection();
        LOGGER.info("Go to travel result page");
        homePage.goToTravelResultsPage();

        TravelResultPage travelResultPage = new TravelResultPage(this.driver);
        LOGGER.info("Verify at least three cards displayed");
        travelResultPage.verifyAtLeastThreeCardsDisplayed();
        LOGGER.info("Verify categories are functional");
        travelResultPage.verifyCategoriesAreFunctional();
        LOGGER.info("end test 2");
       //Artifact.upload(new File(TestBase.videoPath), TestBase.videoPath);
        ArtifactReference.attach("Video", TestBase.videoPath);


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
