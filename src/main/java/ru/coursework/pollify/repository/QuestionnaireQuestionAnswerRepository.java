package ru.coursework.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.entity.QuestionnaireQuestionAnswer;

public interface QuestionnaireQuestionAnswerRepository extends JpaRepository<QuestionnaireQuestionAnswer, Long> {
}
