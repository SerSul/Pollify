package ru.coursework.pollify.dto;

public record QuestionnaireDTO(
        String title,
        String description,
        boolean is_private
) {}
