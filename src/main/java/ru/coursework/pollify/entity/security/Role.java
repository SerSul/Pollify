package ru.coursework.pollify.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import ru.coursework.pollify.annotations.Meta;
import ru.coursework.pollify.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sh_role")
@Meta(title = "Роль")
public class Role extends BaseEntity {

    @Column(name = "title_p")
    private String title;

    @Column(name = "key_p")
    private String key;

    @OneToMany(mappedBy = "role")
    private List<AssignedPermission> permissions;
}
