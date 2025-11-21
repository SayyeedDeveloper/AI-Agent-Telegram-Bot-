package sayyeed.dev.aiagenttelegrambot.service.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.service.UserAiLogsService;

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
        return askAIWithCustomSystem(userId, prompt, SYSTEM_PROMPT);
    }

    /**
     * Ask AI with a custom system prompt (used by RAG system)
     * Note: This method does NOT load history from database as it's provided in the system prompt by ContextAdvisor
     *
     * @param userId User identifier
     * @param prompt User's prompt
     * @param systemPrompt Custom system prompt with context
     * @return AI response
     */
    public String askAIWithCustomSystem(String userId, String prompt, String systemPrompt) {
        String response = client.prompt()
                .system(systemPrompt)
                .user(prompt)
                .call()
                .content();
        logsService.saveLog(userId, prompt, response);
        return response;
    }

}
