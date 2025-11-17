package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class MessageService {

    private final TelegramClient telegramClient;

    public MessageService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void handleMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        String userName = update.getMessage().getFrom().getFirstName();

        System.out.println("Received from " + userName + ": " + messageText);

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text("Welcome, how can I help you?")
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        System.out.println("Callback received: " + callbackData);
    }
}
