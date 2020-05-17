package goBear.objects;

import java.util.List;

public class Filter {
    private boolean useFilter;
    private String promotion;
    private List<String> insurerList;
    private String personalAccidentOffset;
    private String medicalExpensesOffset;
    private String tripCancellationOffset;
    private String tripTerminationOffset;
    private String personalLossOffset;

    public boolean isUseFilter() {
        return useFilter;
    }

    public String getPromotion() {
        return promotion;
    }

    public List<String> getInsurerList() {
        return insurerList;
    }

    public String getPersonalAccidentOffset() {
        return personalAccidentOffset;
    }

    public String getMedicalExpensesOffset() {
        return medicalExpensesOffset;
    }

    public String getTripCancellationOffset() {
        return tripCancellationOffset;
    }

    public String getTripTerminationOffset() {
        return tripTerminationOffset;
    }

    public String getPersonalLossOffset() {
        return personalLossOffset;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }

    public void setInsurerList(List<String> insurerList) {
        this.insurerList = insurerList;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setPersonalAccidentOffset(String personalAccidentOffset) {
        this.personalAccidentOffset = personalAccidentOffset;
    }

    public void setMedicalExpensesOffset(String medicalExpensesOffset) {
        this.medicalExpensesOffset = medicalExpensesOffset;
    }

    public void setPersonalLossOffset(String personalLossOffset) {
        this.personalLossOffset = personalLossOffset;
    }

    public void setTripCancellationOffset(String tripCancellationOffset) {
        this.tripCancellationOffset = tripCancellationOffset;
    }

    public void setTripTerminationOffset(String tripTerminationOffset) {
        this.tripTerminationOffset = tripTerminationOffset;
    }
}
