package com.hant.trade.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();

        String userName = user.getFirstName() + " " + user.getLastName();
            messageBuilder.append("Welcome ").append(userName).append("\n");
            messageBuilder.append("this bot will demonstrate you the command feature of the TelegramBots API!");

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}