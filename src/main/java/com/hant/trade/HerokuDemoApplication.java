package com.hant.trade;

import com.hant.trade.updateshandlers.CommandsHandler;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;
import org.telegram.telegrambots.meta.logging.BotsFileHandler;

@SpringBootApplication
public class HerokuDemoApplication {
	private static final String LOGTAG = "MAIN";

	public static void main(String[] args) {
		BotLogger.setLevel(Level.ALL);
		BotLogger.registerLogger(new ConsoleHandler());
		try {
			BotLogger.registerLogger(new BotsFileHandler());
		} catch (IOException e) {
			BotLogger.severe(LOGTAG, e);
		}

		try {
			BotLogger.info(LOGTAG, "Starting bot...");

			ApiContextInitializer.init();
			TelegramBotsApi telegramBotsApi = createTelegramBotsApi();
			try {
				// Register long polling bots. They work regardless type of TelegramBotsApi we are creating
				telegramBotsApi.registerBot(new CommandsHandler(BotConfig.COMMANDS_USER));
				BotLogger.info(LOGTAG, "Started bot...");
			} catch (TelegramApiException e) {
				BotLogger.error(LOGTAG, e);
			}
		} catch (Exception e) {
			BotLogger.error(LOGTAG, e);
		}

		SpringApplication.run(HerokuDemoApplication.class, args);

	}


	private static TelegramBotsApi createTelegramBotsApi()  {
		TelegramBotsApi telegramBotsApi;
		telegramBotsApi = createLongPollingTelegramBotsApi();
		return telegramBotsApi;
	}

	/**
	 * @brief Creates a Telegram Bots Api to use Long Polling (getUpdates) bots.
	 * @return TelegramBotsApi to register the bots.
	 */
	private static TelegramBotsApi createLongPollingTelegramBotsApi() {
		return new TelegramBotsApi();
	}
}
