package ru.coursework.pollify.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.pollify.annotations.Meta;
import ru.coursework.pollify.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "sh_assigned_role", uniqueConstraints = @UniqueConstraint( columnNames = {"role_id", "user_id"} ) )
@Meta( title = "Назначенная роль" )
public class AssignedRole extends BaseEntity {

    @ManyToOne
    @JoinColumn( name = "role_id", nullable = false )
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", nullable = false )
    private User user;

}
