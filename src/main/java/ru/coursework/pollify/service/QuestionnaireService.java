package ru.coursework.pollify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.interfaces.QuestionnaireInterface;
import ru.coursework.pollify.service.interfaces.IQuestionnaireService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionnaireService implements IQuestionnaireService {

    private final QuestionnaireInterface questionnaireInterface;

    @Override
    public Questionnaire findQuestionnaireByUri(UUID uri) {
        return questionnaireInterface.findByUri(uri);
    }
}
