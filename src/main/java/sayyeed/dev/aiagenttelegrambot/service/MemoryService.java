package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.entitly.VectorStoreEntity;
import sayyeed.dev.aiagenttelegrambot.repository.VectorStoryRepository;

import java.util.List;

@Service
public class MemoryService {
    private final VectorStoryRepository repository;

    public MemoryService(VectorStoryRepository repository) {
        this.repository = repository;
    }

    public void saveToMemory(String userId, String prompt, List<Double> embedding) {
        VectorStoreEntity vectorStore = new VectorStoreEntity();
        vectorStore.setUser_id(userId);
        vectorStore.setText(prompt);
        vectorStore.setEmbedding(embedding);
        repository.save(vectorStore);
    }
}
