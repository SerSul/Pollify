package ru.coursework.pollify.dto;

public record AddQuestionDTO(
        Long questionnaireId,
        String questionText
){
}
