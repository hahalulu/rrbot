package com.hant.trade.commands;

import com.hant.trade.model.PosInput;
import com.hant.trade.model.PosOutput;
import java.text.DecimalFormat;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

/**
 * This command simply replies with a hello to the users command and
 * sends them the 'kind' words back, which they send via command parameters
 *
 * @author  (Mit0x2)
 */
public class LongCommand extends BotCommand {

    private static final String LOGTAG = "LONGCOMMAND";

    public LongCommand() {
        super("long", "ex: /long bank=100 loss_pct=2 entry=5.5 sl=5.189 tp=6.11");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        PosInput input = new PosInput();
        for (int i = 0; i < arguments.length; i++) {
            input.setTotalBank(Double.valueOf(arguments[0].replace("bank=", "")));
            input.setPercentLoss(Double.valueOf(arguments[1].replace("loss_pct=", "")));
            input.setEntry(Double.valueOf(arguments[2].replace("entry=", "")));
            input.setStopLoss(Double.valueOf(arguments[3].replace("sl=", "")));
            input.setTakeProfit(Double.valueOf(arguments[4].replace("tp=", "")));
        }
        StringBuilder messageTextBuilder = new StringBuilder("Result: ");
        messageTextBuilder.append("\n");

        if (arguments == null || arguments.length < 5) {
            messageTextBuilder.append("You need to type right syntax: totalBank percentLoss entry stopLoss takeProfit\n");
            messageTextBuilder.append(String.join(" ", arguments));
        } else {
            PosOutput longPosOutput = calculateLongRisk(input);
            messageTextBuilder.append("Total bank: ").append(input.getTotalBank()).append("\n");
            messageTextBuilder.append("Percent loss: ").append(input.getPercentLoss()).append("\n");
            messageTextBuilder.append("Entry price: ").append(input.getEntry()).append("\n");
            messageTextBuilder.append("Stop price: ").append(input.getStopLoss()).append("\n");
            messageTextBuilder.append("Take profit: ").append(input.getTakeProfit()).append("\n");
            messageTextBuilder.append("==================\n");
            messageTextBuilder.append("Maximum money: ").append(longPosOutput.getMaxMoney()).append("\n");
            messageTextBuilder.append("Order size: ").append(longPosOutput.getQuantity()).append("\n");
            messageTextBuilder.append("Profit: ").append(longPosOutput.getMaxTakeProfit()).append("\n");
            messageTextBuilder.append("Loss: ").append(longPosOutput.getMaxStopLoss()).append("\n");
            messageTextBuilder.append("RR ratio: ").append(longPosOutput.getRiskRewardRatio()).append("\n");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private static PosOutput calculateLongRisk (PosInput input) {
        PosOutput longPosOutput = new PosOutput();

        Double khoanglo = Double.valueOf(roundAndFormat(input.getEntry()-input.getStopLoss()));
        String maxMoney = roundAndFormat(((input.getTotalBank() * input.getPercentLoss()) / khoanglo) * input.getEntry()/100);
        Double douMaxMoney = Double.valueOf(maxMoney);
        longPosOutput.setMaxMoney(maxMoney);
        longPosOutput.setQuantity(roundAndFormat(douMaxMoney/input.getEntry(), 5));
        longPosOutput.setMaxStopLoss(roundAndFormat(input.getPercentLoss() * input.getTotalBank()/100));

        longPosOutput.setMaxTakeProfit(roundAndFormat(douMaxMoney * ((input.getTakeProfit()/input.getEntry()) - 1)));
        longPosOutput.setRiskRewardRatio(roundAndFormat(((input.getTakeProfit() - input.getEntry()) / khoanglo)));

        return longPosOutput;
    }

    private static String roundAndFormat(Double input, int maxFrac) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(maxFrac);
        return df.format(input);
    }

    private static String roundAndFormat(Double input) {
        return roundAndFormat(input, 4);
    }

    public static void main(String[] args) {
        PosInput input = new PosInput();
//        input.setTotalBank(100d);
//        input.setEntry(5.5);
//        input.setPercentLoss(2d);
//        input.setStopLoss(5.189);
//        input.setTakeProfit(6.11);
        input.setTotalBank(100d);
        input.setEntry(32000d);
        input.setPercentLoss(2d);
        input.setStopLoss(28000d);
        input.setTakeProfit(42000d);
        System.out.println(calculateLongRisk(input));

    }
}