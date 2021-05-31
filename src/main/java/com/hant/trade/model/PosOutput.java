package com.hant.trade.model;

public class PosOutput {
    private Double maxMoney;
    private Double maxTakeProfit;
    private Double maxStopLoss;
    private Double riskRewardRatio;
    private Double quantity;

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Double getMaxTakeProfit() {
        return maxTakeProfit;
    }

    public void setMaxTakeProfit(Double maxTakeProfit) {
        this.maxTakeProfit = maxTakeProfit;
    }

    public Double getMaxStopLoss() {
        return maxStopLoss;
    }

    public void setMaxStopLoss(Double maxStopLoss) {
        this.maxStopLoss = maxStopLoss;
    }

    public Double getRiskRewardRatio() {
        return riskRewardRatio;
    }

    public void setRiskRewardRatio(Double riskRewardRatio) {
        this.riskRewardRatio = riskRewardRatio;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "LongPosOutput{" +
            "maxMoney=" + maxMoney +
            ", maxTakeProfit=" + maxTakeProfit +
            ", maxStopLoss=" + maxStopLoss +
            ", riskRewardRatio=" + riskRewardRatio +
            ", quantity=" + quantity +
            '}';
    }
}
