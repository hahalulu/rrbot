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
public class ShortCommand extends BotCommand {

    private static final String LOGTAG = "LONGCOMMAND";

    public ShortCommand() {
        super("short", "syntax: totalBank percentLoss entry stopLoss takeProfit");
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
            PosOutput posOutput = calculateLongRisk(input);
            messageTextBuilder.append("Total bank: ").append(input.getTotalBank()).append("\n");
            messageTextBuilder.append("Percent loss: ").append(input.getPercentLoss()).append("\n");
            messageTextBuilder.append("Entry price: ").append(input.getEntry()).append("\n");
            messageTextBuilder.append("Stop price: ").append(input.getStopLoss()).append("\n");
            messageTextBuilder.append("Take profit: ").append(input.getTakeProfit()).append("\n");
            messageTextBuilder.append("==================");
            messageTextBuilder.append("Maximum money: ").append(posOutput.getMaxMoney()).append("\n");
            messageTextBuilder.append("Order size: ").append(posOutput.getQuantity()).append("\n");
            messageTextBuilder.append("Profit: ").append(posOutput.getMaxTakeProfit()).append("\n");
            messageTextBuilder.append("Loss: ").append(posOutput.getMaxStopLoss()).append("\n");
            messageTextBuilder.append("RR ratio: ").append(posOutput.getRiskRewardRatio()).append("\n");
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
        PosOutput posOutput = new PosOutput();

        Double khoanglo = roundAndFormat(input.getEntry()-input.getStopLoss());
        Double maxMoney = roundAndFormat(((input.getTotalBank() * input.getPercentLoss()) / khoanglo) * input.getEntry()/100);
        posOutput.setMaxMoney(maxMoney);
        posOutput.setQuantity(roundAndFormat(maxMoney/input.getEntry()));
        posOutput.setMaxStopLoss(roundAndFormat(input.getPercentLoss() * input.getTotalBank()/100));

        posOutput.setMaxTakeProfit(roundAndFormat(maxMoney * ((input.getEntry() - input.getTakeProfit())/input.getEntry())));
        posOutput.setRiskRewardRatio(roundAndFormat(((input.getTakeProfit() - input.getEntry()) / khoanglo)));

        return posOutput;
    }

    private static Double roundAndFormat(Double input) {
        DecimalFormat df = new DecimalFormat("#.####");
        return Double.valueOf(df.format(input));
    }

    public static void main(String[] args) {
        PosInput input = new PosInput();
        input.setEntry(27460d);
        input.setPercentLoss(2d);
        input.setTotalBank(379d);
        input.setStopLoss(27550d);
        input.setTakeProfit(26465d);
        System.out.println(calculateLongRisk(input));
    }
}