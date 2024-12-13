package ru.coursework.pollify.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionnaireQuestionAnswer> questionnaireQuestionAnswers;
}
