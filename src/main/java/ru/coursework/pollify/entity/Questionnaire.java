package ru.coursework.pollify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "sh_questionnaire")
@Meta(title = "Анкета")
public class Questionnaire extends BaseEntity {


    @Meta(title = "Наименование")
    @Column(name = "title_p")
    private String title;

    @Meta(title = "Описание")
    @Column(name = "description_p")
    private String description;


    @OneToMany(mappedBy = "questionnaire")
    private List<QuestionnaireAnswer> answers;

    @Meta(title = "Токен доступа к просмотру прохождений")
    @Column(name = "access_token_p", unique = true, nullable = false)
    private UUID accessToken;

    @Meta(title = "Уникальная ссылка")
    @Column(name = "uri_p", unique = true, nullable = false)
    private UUID uri;

}
