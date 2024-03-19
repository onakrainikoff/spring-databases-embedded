package ru.on8off.databases.embedded.hsqldb.repository.entity;

import jakarta.persistence.AttributeConverter;

public class ElementGroupTypeConverter implements AttributeConverter<ElementGroupType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ElementGroupType attribute) {
        return attribute.getId();
    }

    @Override
    public ElementGroupType convertToEntityAttribute(Integer dbData) {
        return ElementGroupType.valueOf(dbData);
    }
}
