package ru.coursework.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.QuestionnaireAnswer;
import ru.coursework.pollify.entity.QuestionnaireQuestionAnswer;

public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {
}
