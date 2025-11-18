package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.entitly.UserAiLogsEntity;
import sayyeed.dev.aiagenttelegrambot.repository.UserAiLogsRepository;

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
}
