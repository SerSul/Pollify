package ru.coursework.pollify.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "sh_questionnaire_question_answer")
@Meta(title = "Ответ на вопрос анкеты")
public class QuestionnaireQuestionAnswer extends BaseEntity {

    @Meta(title = "Ответ")
    @Column(name = "answer_p")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionnaireQuestion question;

    @ManyToOne
    @JoinColumn(name = "questionnaire_answer_id")
    private QuestionnaireAnswer questionnaireAnswer;
}
