package goBear.objects;

public class Details {
    private boolean useDetails;
    private String policyType;
    private String whoGoing;
    private String destination;
    private String startDate;
    private String endDate;

    public boolean isUseDetails() {
        return useDetails;
    }

    public String getPolicyType() {
        return policyType;
    }

    public String getWhoGoing() {
        return whoGoing;
    }

    public String getDestination() {
        return destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setUseDetails(boolean useDetails) {
        this.useDetails = useDetails;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public void setWhoGoing(String whoGoing) {
        this.whoGoing = whoGoing;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}