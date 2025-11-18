package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiChatService {
    private final ChatClient client;
    private final UserAiLogsService logsService;

    public GeminiChatService(ChatClient client, UserAiLogsService logsService) {
        this.client = client;
        this.logsService = logsService;
    }

    public String askAI(String userId, String prompt) {
        String response = client.prompt()
                .system("Don't send markdown characters ('*'.. e.g)")
                .user(prompt)
                .call()
                .content();
        logsService.saveLog(userId, prompt, response);
        return response;
    }

}
