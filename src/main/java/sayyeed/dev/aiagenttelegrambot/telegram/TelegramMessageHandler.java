package sayyeed.dev.aiagenttelegrambot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import sayyeed.dev.aiagenttelegrambot.service.MessageService;

/**
 * Simple router - just delegates to service
 * Service has full access to Update and TelegramClient
 */
@Component
public class TelegramMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(TelegramMessageHandler.class);

    private final MessageService messageService;

    public TelegramMessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Routes updates to appropriate service methods
     */
    public void handleUpdate(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // Route text messages
                messageService.handleMessage(update);
            } else if (update.hasCallbackQuery()) {
                // Route button clicks
                messageService.handleCallbackQuery(update);
            }
            // Add more routing as needed:
            // - update.hasInlineQuery()
            // - update.hasEditedMessage()
            // - etc.
        } catch (Exception e) {
            log.error("Error handling update", e);
        }
    }
}
