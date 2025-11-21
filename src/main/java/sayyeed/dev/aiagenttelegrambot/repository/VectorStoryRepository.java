package sayyeed.dev.aiagenttelegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import sayyeed.dev.aiagenttelegrambot.entitly.VectorStoreEntity;

public interface VectorStoryRepository extends CrudRepository<VectorStoreEntity, String> {
}
