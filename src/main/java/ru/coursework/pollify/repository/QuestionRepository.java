package ru.coursework.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.entity.QuestionnaireQuestion;

public interface QuestionRepository extends JpaRepository<QuestionnaireQuestion, Long> {
}
