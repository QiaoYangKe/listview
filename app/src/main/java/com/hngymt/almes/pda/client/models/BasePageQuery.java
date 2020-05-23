package com.hngymt.almes.pda.client.models;

public class BasePageQuery {
    private String sorting = "id asc";
    private int skipCount;
    private int maxResultCount = 10;
    private int page = 1;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        this.setSkipCount((this.page-1)*this.maxResultCount);
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public int getMaxResultCount() {
        return maxResultCount;
    }

    public void setMaxResultCount(int maxResultCount) {
        this.maxResultCount = maxResultCount;
    }

    public String getSorting() {
        return sorting;
    }
}