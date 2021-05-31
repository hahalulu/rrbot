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

public class ShortCommand extends BotCommand {

    private static final String LOGTAG = "SHORTCOMMAND";

    public ShortCommand() {
        super("short", "ex: /short bank=100 loss_pct=2 entry=5.5 sl=6.11 tp=5.189");
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
        StringBuilder messageTextBuilder = new StringBuilder();

        if (arguments == null || arguments.length < 5) {
            messageTextBuilder.append("You need to type right syntax: /short bank=100 loss_pct=2 entry=5.5 sl=6.11 tp=5.189\n");
            messageTextBuilder.append(String.join(" ", arguments));
        } else {
            PosOutput posOutput = calculateShortRisk(input);
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

    private static PosOutput calculateShortRisk(PosInput input) {
        PosOutput posOutput = new PosOutput();

        Double khoanglo = input.getStopLoss()-input.getEntry();
        Double maxMoney = ((input.getTotalBank() * input.getPercentLoss()) / khoanglo) * input.getEntry()/100;

        posOutput.setMaxMoney(maxMoney);
        posOutput.setQuantity(maxMoney/input.getEntry());
        posOutput.setMaxStopLoss(input.getPercentLoss() * input.getTotalBank()/100);

        posOutput.setMaxTakeProfit((maxMoney * ((input.getEntry() - input.getTakeProfit())/input.getEntry())));
        posOutput.setRiskRewardRatio(((input.getEntry() - input.getTakeProfit()) / khoanglo));

        return posOutput;
    }

    public static void main(String[] args) {

        PosInput input = new PosInput();
        input.setTotalBank(0.04997);
        input.setEntry(0.00004490);
        input.setPercentLoss(8d);
        input.setStopLoss(0.00004630);
        input.setTakeProfit(0.00003960);
        PosOutput posOutput = calculateShortRisk(input);
        System.out.println(posOutput);

        StringBuilder messageTextBuilder = Util.displayResult(input, posOutput);
        System.out.println(messageTextBuilder);
    }
}