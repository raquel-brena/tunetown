package com.imd.backend.app.gateway.aiGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Tool exposto ao agente para consultas somente leitura no banco.
 */
@Component
public class DbQueryTool {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DbQueryTool(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Tool(name = "db_query", value = "Executa consultas SQL somente leitura e retorna os resultados em JSON.")
    public String run(String sql) {
        if (!StringUtils.hasText(sql)) {
            return "SQL vazio.";
        }
        String trimmed = sql.trim().toLowerCase();
        if (!trimmed.startsWith("select")) {
            return "Apenas consultas SELECT são permitidas.";
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        try {
            return objectMapper.writeValueAsString(rows);
        } catch (JsonProcessingException e) {
            return rows.toString();
        }
    }
}
