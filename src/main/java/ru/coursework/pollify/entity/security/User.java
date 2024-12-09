package ru.coursework.pollify.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.pollify.annotations.Meta;
import ru.coursework.pollify.entity.BaseEntity;

import java.util.List;


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AssignedRole> roles;
}
