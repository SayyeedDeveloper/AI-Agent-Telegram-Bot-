package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.ai.document.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemanticSearchService {
    private final JdbcTemplate jdbcTemplate;

    public SemanticSearchService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> searchTopK(String userId, List<Double> embedding, int topK) {

        String sql = """
            SELECT text
            FROM vector_store
            WHERE user_id = ?
            ORDER BY embedding <=> CAST(? AS vector)
            LIMIT ?
        """;

        String vector = toPgVector(embedding);
        return jdbcTemplate.query(
                sql,
                new Object[]{userId, vector, topK},
                (rs, rowNum) -> rs.getString("text")
        );
    }

    private String toPgVector(List<Double> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
