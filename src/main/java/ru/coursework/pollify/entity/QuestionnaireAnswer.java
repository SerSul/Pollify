package ru.coursework.pollify.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;
import ru.coursework.pollify.entity.security.PersonalData;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
@Builder
@Table( name = "sh_questionnaire_answer" )
@Meta( title = "Ответ на анкету" )
public class QuestionnaireAnswer extends BaseEntity {


    @Meta(title = "Дата и время")
    @Column(name = "date_p")
    private Date date;

    @Meta( title = "Анкета")
    @ManyToOne
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Meta( title = "Персона")
    @ManyToOne
    @JoinColumn(name = "personal_data_id")
    private PersonalData personalData;


    @OneToMany(mappedBy = "questionnaireAnswer", cascade = CascadeType.ALL)
    private List<QuestionnaireQuestionAnswer> questionAnswers;
}
