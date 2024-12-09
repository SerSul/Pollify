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
@Table( name = "sh_assigned_permission" )
@Meta( title = "Назначенное право доступа" )
public class AssignedPermission extends BaseEntity {

    @Column( name = "permission_p", nullable = false )
    private String permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id" )
    private User user;

    @ManyToOne
    @JoinColumn( name = "role_id" )
    private Role role;

}
