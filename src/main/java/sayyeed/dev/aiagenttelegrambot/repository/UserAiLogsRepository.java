package sayyeed.dev.aiagenttelegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import sayyeed.dev.aiagenttelegrambot.entitly.UserAiLogsEntity;

public interface UserAiLogsRepository extends CrudRepository<UserAiLogsEntity, String> {
}
