package sayyeed.dev.aiagenttelegrambot.telegram;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Consumes updates from Telegram
 * Delegates to handler for processing
 */
public class TelegramBotUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramMessageHandler messageHandler;

    public TelegramBotUpdateConsumer(TelegramMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void consume(Update update) {
        messageHandler.handleUpdate(update);
    }
}
