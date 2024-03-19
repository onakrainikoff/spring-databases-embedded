package ru.on8off.databases.embedded.hsqldb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.databases.embedded.hsqldb.repository.entity.Element;
import ru.on8off.databases.embedded.hsqldb.repository.entity.ElementGroup;
import ru.on8off.databases.embedded.hsqldb.repository.entity.ElementGroupType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ElementRepositoryIT {
    @Autowired
    ElementRepository elementRepository;
    @Autowired
    ElementGroupRepository elementGroupRepository;

    @Test
    @Transactional
    @Rollback
    void testCRUD() {
        var group = new ElementGroup();
        group.setName("Test Group - " + UUID.randomUUID());
        group.setType(ElementGroupType.TYPE1);
        group = elementGroupRepository.save(group);

        var name = "Test Element - " + UUID.randomUUID();
        var elements = elementRepository.findElementsByName(name);
        assertEquals(0, elements.size());

        var element = new Element();
        element.setName(name);
        element.setElementGroup(group);

        var resultElement = elementRepository.save(element);
        assertNotNull(resultElement);
        assertNotNull(resultElement.getId());
        assertEquals(element.getDateCreated(), resultElement.getDateCreated());
        assertEquals(element.getName(), resultElement.getName());
        assertEquals(element.getElementGroup(), resultElement.getElementGroup());

        element = elementRepository.findById(resultElement.getId()).orElse(null);
        assertNotNull(element);

        elements = elementRepository.findElementsByName(name);
        assertEquals(1, elements.size());
        assertTrue(elements.contains(element));

        elementRepository.deleteById(element.getId());
        element = elementRepository.findById(resultElement.getId()).orElse(null);
        assertNull(element);
    }
}