package com.hant.trade.commands;

import com.hant.trade.model.PosInput;
import com.hant.trade.model.PosOutput;
import java.text.DecimalFormat;

public class Util {
    public static String roundAndFormat(Double input, int maxFrac) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(maxFrac);
        return df.format(input);
    }

    public static String roundAndFormat(Double input) {
        return roundAndFormat(input, 4);
    }

    public static StringBuilder displayResult(PosInput input, PosOutput output) {
        StringBuilder messageTextBuilder = new StringBuilder("Result: \n");

        messageTextBuilder.append("Total bank: ").append(Util.roundAndFormat(input.getTotalBank(), 8)).append("\n");
        messageTextBuilder.append("Percent loss: ").append(Util.roundAndFormat(input.getPercentLoss())).append("\n");
        messageTextBuilder.append("Entry price: ").append(Util.roundAndFormat(input.getEntry(), 8)).append("\n");
        messageTextBuilder.append("Stop price: ").append(Util.roundAndFormat(input.getStopLoss(), 8)).append("\n");
        messageTextBuilder.append("Take profit: ").append(Util.roundAndFormat(input.getTakeProfit(), 8)).append("\n");
        messageTextBuilder.append("==================\n");
        messageTextBuilder.append("Maximum money: ").append(Util.roundAndFormat(output.getMaxMoney())).append("\n");
        messageTextBuilder.append("Order size: ").append(Util.roundAndFormat(output.getQuantity(), 5)).append("\n");
        messageTextBuilder.append("Profit: ").append(Util.roundAndFormat(output.getMaxTakeProfit(), 8)).append("\n");
        messageTextBuilder.append("Loss: ").append(Util.roundAndFormat(output.getMaxStopLoss(), 8)).append("\n");
        messageTextBuilder.append("RR ratio: ").append(Util.roundAndFormat(output.getRiskRewardRatio())).append("\n");

        return messageTextBuilder;
    }

}
