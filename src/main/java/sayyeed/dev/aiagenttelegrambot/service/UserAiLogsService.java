package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.entitly.UserAiLogsEntity;
import sayyeed.dev.aiagenttelegrambot.repository.UserAiLogsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAiLogsService {

    private final UserAiLogsRepository repository;

    public UserAiLogsService(UserAiLogsRepository repository) {
        this.repository = repository;
    }


    public void saveLog(String userId, String prompt, String response) {
        UserAiLogsEntity entity = new UserAiLogsEntity();
        entity.setUserId(userId);
        entity.setPrompt(prompt);
        entity.setResponse(response);
        repository.save(entity);
    }

    public List<Message> getUserHistory(String userId, int n) {
        List<UserAiLogsEntity> logs = repository.findByUserIdOrderByLocalDateTimeDesc(
                userId,
                PageRequest.of(0, n)
        );

        List<Message> messages = new ArrayList<>();

        for (int i = logs.size() - 1; i >= 0; i--) {
            UserAiLogsEntity log = logs.get(i);
            messages.add(new UserMessage(log.getPrompt()));
            messages.add(new AssistantMessage(log.getResponse()));
        }

        return messages;
    }

    public List<String> getUserHistoryFormatted(String userId, int n) {
        List<UserAiLogsEntity> logs = repository.findByUserIdOrderByLocalDateTimeDesc(
                userId,
                PageRequest.of(0, n)
        );

        List<String> formattedMessages = new ArrayList<>();

        for (int i = logs.size() - 1; i >= 0; i--) {
            UserAiLogsEntity log = logs.get(i);
            formattedMessages.add("User: " + log.getPrompt());
            formattedMessages.add("Assistant: " + log.getResponse());
        }

        return formattedMessages;
    }

}
