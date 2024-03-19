package ru.on8off.databases.embedded.hsqldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.on8off.databases.embedded.hsqldb.repository.entity.ElementGroup;
import ru.on8off.databases.embedded.hsqldb.repository.entity.ElementGroupType;


import java.util.List;

@Repository
public interface ElementGroupRepository extends JpaRepository<ElementGroup, Integer>, JpaSpecificationExecutor<ElementGroup> {

    ElementGroup findElementGroupByName(String name);

    @Query("select g from ElementGroup g where  g.type = :type")
    List<ElementGroup> findElementGroupsByType(@Param("type") ElementGroupType elementGroupType);
}
