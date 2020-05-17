package goBear.initiations;

import automationLibrary.actions.GeneralAction;
import automationLibrary.initiations.Configurations;
import goBear.objects.Details;
import goBear.objects.Filter;
import goBear.objects.Sort;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestConfigurations {

    public static JSONObject dataObject = new JSONObject(GeneralAction.readFile(Configurations.TEST_DATA_FILE_PATH));
    public static String homePageUrl;
    public static Filter testDataFilter;
    public static Sort testDataSort;
    public static Details testDataDetails;

    public static void initTestData(String environment) {
        switch (environment) {
            case "production":
                JSONObject productionObject = dataObject.getJSONObject("production");
                homePageUrl = productionObject.getString("homepage_url");

                JSONObject filterObject = productionObject.getJSONObject("filter");
                Filter filter = new Filter();
                filter.setPromotion(filterObject.getString("promotion"));
                List<String> insurerList = new ArrayList<>();
                JSONArray insurerArray = filterObject.getJSONArray("insurers");
                for(int i = 0; i < insurerArray.length(); i++) {
                    insurerList.add(insurerArray.getString(i));
                }
                filter.setInsurerList(insurerList);
                filter.setPersonalAccidentOffset(filterObject.getString("personal_accident_offset"));
                testDataFilter = filter;

                JSONObject sortObject = productionObject.getJSONObject("sort");
                Sort sort = new Sort();
                sort.setSortOption(sortObject.getString("sort_option"));
                testDataSort = sort;

                JSONObject detailObject = productionObject.getJSONObject("details");
                Details details = new Details();
                details.setPolicyType(detailObject.getString("policy_type"));
                details.setDestination(detailObject.getString("destination"));
                details.setWhoGoing(detailObject.getString("who_going"));
                details.setStartDate(detailObject.getString("start_date"));
                details.setEndDate(detailObject.getString("end_date"));
                testDataDetails = details;
                break;
        }
    }
}
