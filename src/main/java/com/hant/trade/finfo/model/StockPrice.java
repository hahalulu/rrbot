package com.hant.trade.finfo.model;

public class StockPrice {
    private String code;
    private String date;
    private String floor;
    private String open;
    private String high;
    private String low;
    private String close;
    private String nmVolume;
    private String ptVolume;
    private String pctChange;
    private String lastPrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getNmVolume() {
        return nmVolume;
    }

    public void setNmVolume(String nmVolume) {
        this.nmVolume = nmVolume;
    }

    public String getPtVolume() {
        return ptVolume;
    }

    public void setPtVolume(String ptVolume) {
        this.ptVolume = ptVolume;
    }

    public String getPctChange() {
        return pctChange;
    }

    public void setPctChange(String pctChange) {
        this.pctChange = pctChange;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }
}
