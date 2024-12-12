package ru.coursework.pollify.dto;

import java.util.UUID;

public record QuestionnaireCredentialsDTO(
        String uri,
        String accessToken,
        Long questionnaireId
) {
}
