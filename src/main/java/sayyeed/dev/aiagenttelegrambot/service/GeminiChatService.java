package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiChatService {
    private final ChatClient client;
    private final UserAiLogsService logsService;

    private static final int HISTORY_LIMIT = 10;
    private static final String SYSTEM_PROMPT = "You are Helpful AI Assistant for Personal Productivity. Work as Second brain" +
            "Rules(don't send any markdown characters ' * ... e.g'!, you are ai agent telegram, use telegram own bold italic or other text decorators instead of markdown";

    public GeminiChatService(ChatClient client, UserAiLogsService logsService) {
        this.client = client;
        this.logsService = logsService;
    }

    public String askAI(String userId, String prompt) {
        List<Message> history = logsService.getUserHistory(userId, HISTORY_LIMIT);
        String response = client.prompt()
                .messages(history)
                .system(SYSTEM_PROMPT)
                .user(prompt)
                .call()
                .content();
        logsService.saveLog(userId, prompt, response);
        return response;
    }

}
