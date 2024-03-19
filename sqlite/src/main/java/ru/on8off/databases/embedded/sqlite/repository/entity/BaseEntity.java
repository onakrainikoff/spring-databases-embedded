package ru.on8off.databases.embedded.sqlite.repository.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity<ID>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected ID id;

    @Column(name = "date_created", nullable = false)
    protected ZonedDateTime dateCreated = ZonedDateTime.now();

    public BaseEntity(){

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public boolean equals(Object object) {
        if(!this.getClass().isInstance(object)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity)object;
            return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.getId() + ")";
    }
}
