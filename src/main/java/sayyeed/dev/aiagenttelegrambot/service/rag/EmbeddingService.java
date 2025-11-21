package sayyeed.dev.aiagenttelegrambot.service.rag;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public List<Double> getEmbedding(String userPrompt) {
        EmbeddingResponse response = embeddingModel.embedForResponse(List.of(userPrompt));
        float[] embedding = response.getResults().get(0).getOutput();

        List<Double> result = new ArrayList<>(embedding.length);
        for (float value : embedding) {
            result.add((double) value);
        }
        return result;
    }
}
