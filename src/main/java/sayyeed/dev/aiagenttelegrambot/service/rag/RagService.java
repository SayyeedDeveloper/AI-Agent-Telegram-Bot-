package sayyeed.dev.aiagenttelegrambot.service.rag;

import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.service.UserAiLogsService;

import java.util.List;

@Service
public class RagService {
    private final EmbeddingService embeddingService;
    private final MemoryService memoryService;
    private final SemanticSearchService semanticSearchService;
    private final UserAiLogsService userAiLogsService;
    private final ContextAdvisor contextAdvisor;
    private final GeminiChatService geminiChatService;

    public RagService(EmbeddingService embeddingService,
                     MemoryService memoryService,
                     SemanticSearchService semanticSearchService,
                     UserAiLogsService userAiLogsService,
                     ContextAdvisor contextAdvisor,
                     GeminiChatService geminiChatService) {
        this.embeddingService = embeddingService;
        this.memoryService = memoryService;
        this.semanticSearchService = semanticSearchService;
        this.userAiLogsService = userAiLogsService;
        this.contextAdvisor = contextAdvisor;
        this.geminiChatService = geminiChatService;
    }

    private static final String BASE_SYSTEM_PROMPT = "You are a Helpful AI Assistant for Personal Productivity. " +
            "Work as a Second Brain to help users organize, recall, and utilize information effectively. " +
            "Rules: Don't send any markdown characters ('*', '_', etc.)! You are an AI agent for Telegram. " +
            "Use Telegram's own bold, italic, or other text decorators instead of markdown.";

    public String chat(String userID, String rawPrompt) {
        // Check if user wants to remember this information
        boolean shouldRemember = rawPrompt.contains("/remember");

        // Clean the prompt by removing /remember command
        String finalPrompt = shouldRemember
                ? rawPrompt.replace("/remember", "").trim()
                : rawPrompt.trim();

        // If prompt is empty after removing /remember, return instruction
        if (finalPrompt.isBlank() && shouldRemember) {
            return "Please provide text after /remember command. Example: /remember My birthday is January 15th";
        }

        // Generate embedding for semantic search
        List<Double> embedding = embeddingService.getEmbedding(finalPrompt);

        // Save to long-term memory if requested
        if (shouldRemember) {
            memoryService.saveToMemory(userID, finalPrompt, embedding);
        }

        // Retrieve relevant long-term memories via semantic search
        List<String> longTermMemories = semanticSearchService.searchTopK(userID, embedding, 3);

        // Retrieve short-term conversation history
        List<String> shortTermHistory = userAiLogsService.getUserHistoryFormatted(userID, 10);

        // Build enhanced system prompt with context
        String enhancedSystemPrompt = contextAdvisor.buildEnhancedSystemPrompt(
                BASE_SYSTEM_PROMPT,
                longTermMemories,
                shortTermHistory
        );

        // Get AI response with enhanced context
        String response = geminiChatService.askAIWithCustomSystem(
                userID,
                finalPrompt,
                enhancedSystemPrompt
        );

        // Add memory confirmation if information was saved
        if (shouldRemember) {
            response = "✓ Saved to memory.\n\n" + response;
        }

        return response;
    }
}
