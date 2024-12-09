package ru.coursework.pollify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "sh_questionnaire_question")
@Meta(title = "Вопрос анкеты")
public class QuestionnaireQuestion extends BaseEntity{

    @Meta(title = "Текст вопроса")
    @Column(name = "text_p")
    private String text;

    @Meta(title = "Порядок")
    @Column(name = "sequence_p")
    private int sequence = 0;

}
