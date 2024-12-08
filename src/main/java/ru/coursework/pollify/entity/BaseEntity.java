package ru.coursework.pollify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.pollify.entity.iEntity.IEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements IEntity, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "entity_created")
    protected Date created = new Date();

    @Column(name = "entity_updated")
    protected Date updated = new Date();

    @Column(name = "entity_uuid")
    protected UUID uuid = UUID.randomUUID();


}
