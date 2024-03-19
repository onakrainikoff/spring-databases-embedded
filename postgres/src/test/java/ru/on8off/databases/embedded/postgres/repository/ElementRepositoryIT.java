package ru.on8off.databases.embedded.postgres.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.databases.embedded.postgres.repository.ElementGroupRepository;
import ru.on8off.databases.embedded.postgres.repository.ElementRepository;
import ru.on8off.databases.embedded.postgres.repository.entity.Element;
import ru.on8off.databases.embedded.postgres.repository.entity.ElementGroup;
import ru.on8off.databases.embedded.postgres.repository.entity.ElementGroupType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNull(resultElement.getParams());
        assertNull(resultElement.getTags());
        assertNull(resultElement.getItems());

        element = elementRepository.findById(resultElement.getId()).orElse(null);
        assertNotNull(element);

        elements = elementRepository.findElementsByName(name);
        assertEquals(1, elements.size());
        assertTrue(elements.contains(element));

        elementRepository.deleteById(element.getId());
        element = elementRepository.findById(resultElement.getId()).orElse(null);
        assertNull(element);
    }

    @Test
    @Transactional
    @Rollback
    public void testHStore(){
        var group = new ElementGroup();
        group.setName("Test Group - " + UUID.randomUUID());
        group.setType(ElementGroupType.TYPE1);
        group = elementGroupRepository.save(group);
        var name = "Test Element - " + UUID.randomUUID();
        var element = new Element();
        element.setName(name);
        element.setElementGroup(group);
        element.setParams(Map.of("testKey", "testValue"));
        var resultElement = elementRepository.save(element);
        assertNotNull(resultElement.getParams());
        assertEquals("testValue", resultElement.getParams().get("testKey"));
    }

    @Test
    @Transactional
    @Rollback
    public void testArray(){
        var group = new ElementGroup();
        group.setName("Test Group - " + UUID.randomUUID());
        group.setType(ElementGroupType.TYPE1);
        group = elementGroupRepository.save(group);
        var name = "Test Element - " + UUID.randomUUID();
        var element = new Element();
        element.setName(name);
        element.setElementGroup(group);
        element.setTags(new String[]{"test0", "test1"});
        var resultElement = elementRepository.save(element);
        assertNotNull(resultElement.getTags());
        assertEquals(2, resultElement.getTags().length);
        assertEquals("test1", resultElement.getTags()[1]);
    }


    @Test
    @Transactional
    @Rollback
    public void testJsonB(){
        var group = new ElementGroup();
        group.setName("Test Group - " + UUID.randomUUID());
        group.setType(ElementGroupType.TYPE1);
        group = elementGroupRepository.save(group);
        var name = "Test Element - " + UUID.randomUUID();
        var element = new Element();
        element.setName(name);
        element.setElementGroup(group);
        element.setItems(Map.of("test", List.of("test0", "test1")));
        var resultElement = elementRepository.save(element);
        assertNotNull(resultElement.getItems());
        assertNotNull(resultElement.getItems().get("test"));
        assertEquals(2, resultElement.getItems().get("test").size());
        assertEquals("test1", resultElement.getItems().get("test").get(1));
    }
}