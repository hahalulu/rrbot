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

public class LongCommand extends BotCommand {

    private static final String LOGTAG = "LONGCOMMAND";

    public LongCommand() {
        super("long", "ex: /long bank=100 loss_pct=2 entry=5.5 sl=5.189 tp=6.11");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        PosInput input = new PosInput();
        for (int i = 0; i < arguments.length; i++) {
            input.setTotalBank(Double.valueOf(arguments[0].replace("bank=", "").trim()));
            input.setPercentLoss(Double.valueOf(arguments[1].replace("loss_pct=", "").trim()));
            input.setEntry(Double.valueOf(arguments[2].replace("entry=", "").trim()));
            input.setStopLoss(Double.valueOf(arguments[3].replace("sl=", "").trim()));
            input.setTakeProfit(Double.valueOf(arguments[4].replace("tp=", "").trim()));
        }
        StringBuilder messageTextBuilder = new StringBuilder("Result: ");
        messageTextBuilder.append("\n");

        if (arguments == null || arguments.length < 5) {
            messageTextBuilder.append("You need to type right syntax: /long bank=100 loss_pct=2 entry=5.5 sl=5.189 tp=6.11\n");
            messageTextBuilder.append(String.join(" ", arguments));
        } else {
            PosOutput posOutput = calculateLongRisk(input);
            messageTextBuilder = Util.displayResult(input, posOutput);
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

        Double khoanglo = input.getEntry()-input.getStopLoss();
        Double maxMoney = ((input.getTotalBank() * input.getPercentLoss()) / khoanglo) * input.getEntry()/100;

        longPosOutput.setMaxMoney(maxMoney);
        longPosOutput.setQuantity(maxMoney/input.getEntry());
        longPosOutput.setMaxStopLoss(input.getPercentLoss() * input.getTotalBank()/100);

        longPosOutput.setMaxTakeProfit(maxMoney * ((input.getTakeProfit()/input.getEntry()) - 1));
        longPosOutput.setRiskRewardRatio(((input.getTakeProfit() - input.getEntry()) / khoanglo));

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

        PosOutput posOutput = calculateLongRisk(input);
        System.out.println(posOutput);

        StringBuilder messageTextBuilder = Util.displayResult(input, posOutput);
        System.out.println(messageTextBuilder);
    }
}