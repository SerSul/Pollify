package ru.coursework.pollify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.pollify.annotations.Meta;


@Entity
@Getter
@Setter
@Table(name = "sh_user")
public class User extends BaseEntity {

    @Meta(title = "Имя пользователя")
    @Column(name = "username_p")
    private String username;

    @Meta(title = "Пароль", comment = "Хранится в зашифрованном виде", censored = true)
    @Column(name = "password_p")
    private String password;

    @Meta(title = "Email", censored = true)
    @Column(name = "email_p")
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PersonalData personalData;
}
