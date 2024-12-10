package ru.coursework.pollify.dto;

public record QuestionnaireDTO(
        String title,
        String description,
        Boolean is_private
) {}
