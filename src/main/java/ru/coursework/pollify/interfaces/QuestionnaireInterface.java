package ru.coursework.pollify.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.Questionnaire;

import java.util.UUID;


public interface QuestionnaireInterface extends JpaRepository<Questionnaire, Integer> {
    Questionnaire findByUri(UUID uri);

}
