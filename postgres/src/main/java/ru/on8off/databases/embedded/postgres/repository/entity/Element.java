package ru.on8off.databases.embedded.postgres.repository.entity;


import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;

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

    @Type(StringArrayType.class)
    @Column(name = "tags", columnDefinition = "varchar[]")
    private String[] tags;

    @Column(name="params")
    @Type(PostgreSQLHStoreType.class)
    private Map<String, String> params;

    @Column(name="items")
    @Type(JsonBinaryType.class)
    private Map<String, List<String>> items;
}
