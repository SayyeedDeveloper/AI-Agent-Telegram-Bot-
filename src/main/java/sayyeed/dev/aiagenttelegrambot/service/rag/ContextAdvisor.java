package sayyeed.dev.aiagenttelegrambot.service.rag;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ContextAdvisor formats long-term and short-term memories into structured context
 * for the LLM to provide personalized and context-aware responses.
 */
@Service
public class ContextAdvisor {

    /**
     * Builds an enhanced system prompt with integrated memory context
     *
     * @param baseSystemPrompt The base system prompt defining the AI's role
     * @param longTermMemories List of relevant memories from vector store
     * @param shortTermHistory List of recent conversation history
     * @return Enhanced system prompt with memory context
     */
    public String buildEnhancedSystemPrompt(
            String baseSystemPrompt,
            List<String> longTermMemories,
            List<String> shortTermHistory) {

        StringBuilder enhancedPrompt = new StringBuilder();

        // Add base system prompt
        enhancedPrompt.append(baseSystemPrompt).append("\n\n");

        // Add memory context section
        enhancedPrompt.append("## Your Memory Context\n");

        // Add long-term memories if available
        if (longTermMemories != null && !longTermMemories.isEmpty()) {
            enhancedPrompt.append("### Important Information to Remember:\n");
            for (String memory : longTermMemories) {
                enhancedPrompt.append("- ").append(memory).append("\n");
            }
            enhancedPrompt.append("\n");
        }

        // Add short-term history if available
        if (shortTermHistory != null && !shortTermHistory.isEmpty()) {
            enhancedPrompt.append("### Recent Conversation Context:\n");
            for (String history : shortTermHistory) {
                enhancedPrompt.append(history).append("\n");
            }
            enhancedPrompt.append("\n");
        }

        // Add guidance on using context
        enhancedPrompt.append("Use the above context to provide personalized, ")
                      .append("contextually relevant responses. Reference past information ")
                      .append("when relevant to the current query.");

        return enhancedPrompt.toString();
    }

    /**
     * Formats context information for analysis
     *
     * @param longTermMemories List of relevant memories
     * @param shortTermHistory List of recent history
     * @return Formatted context string
     */
    public String formatContextSummary(List<String> longTermMemories, List<String> shortTermHistory) {
        StringBuilder summary = new StringBuilder();

        summary.append("Context Retrieved:\n");
        summary.append("- Long-term memories: ").append(longTermMemories != null ? longTermMemories.size() : 0).append("\n");
        summary.append("- Short-term history: ").append(shortTermHistory != null ? shortTermHistory.size() : 0).append(" entries\n");

        return summary.toString();
    }

    /**
     * Determines if the context is sufficient for the query
     *
     * @param longTermMemories List of relevant memories
     * @param shortTermHistory List of recent history
     * @return true if context is available, false otherwise
     */
    public boolean hasRelevantContext(List<String> longTermMemories, List<String> shortTermHistory) {
        boolean hasLongTerm = longTermMemories != null && !longTermMemories.isEmpty();
        boolean hasShortTerm = shortTermHistory != null && !shortTermHistory.isEmpty();
        return hasLongTerm || hasShortTerm;
    }
}
