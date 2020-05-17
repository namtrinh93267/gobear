package goBear.objects;

public class Sort {
    private boolean useSort;
    private String sortOption;

    public boolean isUseSort() {
        return useSort;
    }

    public String getSortOption() {
        return sortOption;
    }

    public void setUseSort(boolean useSort) {
        this.useSort = useSort;
    }

    public void setSortOption(String sortOption) {
        this.sortOption = sortOption;
    }
}
