package sayyeed.dev.aiagenttelegrambot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import sayyeed.dev.aiagenttelegrambot.telegram.TelegramBotUpdateConsumer;
import sayyeed.dev.aiagenttelegrambot.telegram.TelegramMessageHandler;

/**
 * Configuration for Telegram Bot
 * Follows Dependency Inversion Principle - depends on abstractions
 */
@Configuration
public class TelegramBotConfig {

    private static final Logger log = LoggerFactory.getLogger(TelegramBotConfig.class);

    private final TelegramBotProperties botProperties;

    public TelegramBotConfig(TelegramBotProperties botProperties) {
        this.botProperties = botProperties;
    }

    /**
     * Creates TelegramClient bean
     */
    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botProperties.getToken());
    }

    /**
     * Registers the bot and starts listening for updates
     */
    @Bean
    public CommandLineRunner startTelegramBot(TelegramMessageHandler messageHandler) {
        return args -> {
            log.info("Starting Telegram Bot...");
            log.info("Bot username: {}", botProperties.getUsername());

            try {
                TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
                TelegramBotUpdateConsumer updateConsumer = new TelegramBotUpdateConsumer(messageHandler);

                botsApplication.registerBot(botProperties.getToken(), updateConsumer);

                log.info("Telegram Bot started successfully!");
            } catch (Exception e) {
                log.error("Failed to start Telegram Bot", e);
                throw e;
            }
        };
    }
}
