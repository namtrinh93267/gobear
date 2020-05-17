package goBear.objects;

import java.util.List;

public class TravelInsuranceSearch {
    private boolean useFilter;
    private String promotion;
    private List<String> insurerList;
    private String personalAccidentMoney;

    private boolean useSort;
    private String sortOption;

    public boolean useDetail;
    private String policyType;
    private String whoGoing;
    private String destination;
    private String startDate;
    private String endDate;

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public List<String> getInsurerList() {
        return insurerList;
    }

    public void setInsurerList(List<String> insurerList) {
        this.insurerList = insurerList;
    }

    public String getPersonalAccidentMoney() {
        return personalAccidentMoney;
    }

    public void setPersonalAccidentMoney(String personalAccidentMoney) {
        this.personalAccidentMoney = personalAccidentMoney;
    }

    public boolean isUseSort() {
        return useSort;
    }

    public void setUseSort(boolean useSort) {
        this.useSort = useSort;
    }

    public String getSortOption() {
        return sortOption;
    }

    public void setSortOption(String sortOption) {
        this.sortOption = sortOption;
    }

    public boolean isUseDetail() {
        return useDetail;
    }

    public void setUseDetail(boolean useDetail) {
        this.useDetail = useDetail;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getWhoGoing() {
        return whoGoing;
    }

    public void setWhoGoing(String whoGoing) {
        this.whoGoing = whoGoing;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
