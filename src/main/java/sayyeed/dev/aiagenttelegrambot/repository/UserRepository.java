package sayyeed.dev.aiagenttelegrambot.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.dev.aiagenttelegrambot.entitly.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    @Query("from UserEntity where chatId = ?1")
    Optional<UserEntity> findByChatId(Long telegramId);

}
