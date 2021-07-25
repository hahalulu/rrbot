package com.hant.trade.finfo.model;

import java.util.List;

public class PageIndexInfo {
    private List<IndexInfo> data;
    private String object;
    private int totalCount;

    public List<IndexInfo> getData() {
        return data;
    }

    public void setData(List<IndexInfo> data) {
        this.data = data;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
