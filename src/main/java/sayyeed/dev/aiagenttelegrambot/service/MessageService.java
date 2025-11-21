package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import sayyeed.dev.aiagenttelegrambot.service.rag.RagService;

@Service
public class MessageService {

    private final TelegramClient telegramClient;
    private final UserService userService;
    private final RagService ragService;

    public MessageService(TelegramClient telegramClient, UserService userService, RagService ragService) {
        this.telegramClient = telegramClient;
        this.userService = userService;
        this.ragService = ragService;
    }

    public void handleMessage(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return; // ignore unsupported messages (photos, stickers, etc.)
        }

        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getFirstName();
        String text = update.getMessage().getText().trim();

        switch (text) {
            case "/start" -> handleStart(chatId, userName);
            default -> handleDefault(chatId, text);
        }

    }

    private void handleStart(Long chatId, String userName) {
        userService.userOnboarding(chatId, userName);
        sendMessage(chatId, "Welcome, This is Gemini AI, How Can I help you? 🤖");
    }

    private void handleDefault(Long chatId, String text) {
        String userId = userService.getUserId(chatId);
        if (userId == null) {
            sendMessage(chatId,"Sorry somthing went wrong 😓. Please /start again");
        }else {
            sendTypingAction(chatId);
            sendMessage(chatId, ragService.chat(userId, text));
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTypingAction(Long chatId) {
        SendChatAction chatAction = SendChatAction.builder()
                .chatId(String.valueOf(chatId))
                .action("typing")
                .build();

        try {
            telegramClient.execute(chatAction);
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
