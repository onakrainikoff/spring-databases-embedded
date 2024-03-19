package ru.on8off.databases.embedded.hsqldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.on8off.databases.embedded.hsqldb.repository.entity.Element;


import java.util.List;

@Repository
public interface ElementRepository  extends JpaRepository<Element, Integer>, JpaSpecificationExecutor<Element> {
    List<Element> findElementsByName(String name);
}
