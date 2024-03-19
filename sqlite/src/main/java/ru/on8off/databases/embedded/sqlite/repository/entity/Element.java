package ru.on8off.databases.embedded.sqlite.repository.entity;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "elements")
@Getter @Setter
public class Element extends BaseEntity<Integer>{

    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @ManyToOne(targetEntity = ElementGroup.class)
    @JoinColumn(name = "element_group")
    private ElementGroup elementGroup;
}
