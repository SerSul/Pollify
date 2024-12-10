package ru.coursework.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Questionnaire findByUri(String uri);
}
