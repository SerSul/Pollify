package ru.coursework.pollify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.pollify.annotations.Meta;
import ru.coursework.pollify.entity.enums.SexEntity;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sh_personal_data")
@Meta(title = "Персональные данные")
public class PersonalData extends BaseEntity {

    @Meta(title = "Фамилия персоны")
    @Column(name = "last_name_p")
    private String lastName;

    @Meta(title = "Имя персоны")
    @Column(name = "first_name_p")
    private String firstName;

    @Meta(title = "Отчество персоны")
    @Column(name = "middle_name_p")
    private String middleName;

    @Meta(title = "Дата рождения")
    @Column(name = "date_of_birth_p")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Meta(title = "Пол")
    private SexEntity sexEntity;

}
