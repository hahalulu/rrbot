package com.hant.trade.model;

public class PosInput {
    private Double totalBank;
    private Double entry;
    private Double takeProfit;
    private Double stopLoss;
    private Double percentLoss;

    public Double getTotalBank() {
        return totalBank;
    }

    public void setTotalBank(Double totalBank) {
        this.totalBank = totalBank;
    }

    public Double getEntry() {
        return entry;
    }

    public void setEntry(Double entry) {
        this.entry = entry;
    }

    public Double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(Double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(Double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Double getPercentLoss() {
        return percentLoss;
    }

    public void setPercentLoss(Double percentLoss) {
        this.percentLoss = percentLoss;
    }
}
