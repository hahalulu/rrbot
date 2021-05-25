package com.hant.trade.model;

public class PosOutput {
    private String maxMoney;
    private String maxTakeProfit;
    private String maxStopLoss;
    private String riskRewardRatio;
    private String quantity;

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getMaxTakeProfit() {
        return maxTakeProfit;
    }

    public void setMaxTakeProfit(String maxTakeProfit) {
        this.maxTakeProfit = maxTakeProfit;
    }

    public String getMaxStopLoss() {
        return maxStopLoss;
    }

    public void setMaxStopLoss(String maxStopLoss) {
        this.maxStopLoss = maxStopLoss;
    }

    public String getRiskRewardRatio() {
        return riskRewardRatio;
    }

    public void setRiskRewardRatio(String riskRewardRatio) {
        this.riskRewardRatio = riskRewardRatio;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
