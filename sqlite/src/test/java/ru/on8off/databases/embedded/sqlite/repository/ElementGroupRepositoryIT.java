package ru.on8off.databases.embedded.sqlite.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.databases.embedded.sqlite.repository.entity.ElementGroup;
import ru.on8off.databases.embedded.sqlite.repository.entity.ElementGroupType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ElementGroupRepositoryIT {
    @Autowired
    ElementGroupRepository elementGroupRepository;

    @Test
    void testCRUD() {
        var name = "Test Group - " + UUID.randomUUID();
        var resultGroup = elementGroupRepository.findElementGroupByName(name);
        assertNull(resultGroup);

        var type = ElementGroupType.TYPE1;
        var resultGroups = elementGroupRepository.findElementGroupsByType(type);
        assertEquals(0, resultGroups.size());

        var group = new ElementGroup();
        group.setName(name);
        group.setType(type);

        resultGroup = elementGroupRepository.save(group);
        assertNotNull(resultGroup);
        assertNotNull(resultGroup.getId());
        assertEquals(group.getDateCreated(), resultGroup.getDateCreated());
        assertEquals(group.getName(), resultGroup.getName());
        assertEquals(group.getType(), resultGroup.getType());

        group = elementGroupRepository.findById(resultGroup.getId()).orElse(null);
        assertNotNull(group);

        resultGroup = elementGroupRepository.findElementGroupByName(name);
        assertEquals(group, resultGroup);

        resultGroups = elementGroupRepository.findElementGroupsByType(type);
        assertEquals(1, resultGroups.size());
        assertTrue(resultGroups.contains(group));

        elementGroupRepository.deleteById(group.getId());
        group = elementGroupRepository.findById(resultGroup.getId()).orElse(null);
        assertNull(group);
    }
}