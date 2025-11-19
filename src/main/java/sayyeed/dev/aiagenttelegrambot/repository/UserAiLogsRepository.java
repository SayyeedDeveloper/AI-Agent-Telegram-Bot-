package sayyeed.dev.aiagenttelegrambot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sayyeed.dev.aiagenttelegrambot.entitly.UserAiLogsEntity;

import java.util.List;

public interface UserAiLogsRepository extends CrudRepository<UserAiLogsEntity, String> {
    List<UserAiLogsEntity> findByUserIdOrderByLocalDateTimeDesc(String userId, Pageable pageable);
}
