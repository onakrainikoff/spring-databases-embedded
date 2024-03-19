package ru.on8off.databases.embedded.postgres.repository.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "element_groups")
@Getter
@Setter
public class ElementGroup extends BaseEntity<Integer>{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "group_type", nullable = false)
    @Convert(converter = ElementGroupTypeConverter.class)
    private ElementGroupType type;
}
