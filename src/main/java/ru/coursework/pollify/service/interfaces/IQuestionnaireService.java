package ru.coursework.pollify.service.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coursework.pollify.entity.Questionnaire;

import java.util.UUID;


public interface IQuestionnaireService {
    Questionnaire findQuestionnaireByUri(UUID uri);
}
