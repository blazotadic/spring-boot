package com.academy.demo.repository;

import com.academy.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>
{
    // JPQL (jezik za pisanje OO upita)

    List<Role> findByName(String name); // SELECT * FROM role WHERE name = "JAVA DEVELOPER"

    List<Role> findByNameStartingWith(String namePart); // SELECT * FROM role WHERE name LIKE '?%';

    List<Role> findByNameEndingWith(String namePart); // SELECT * FROM role WHERE name LIKE '%?';

    List<Role> findByNameContaining(String namePart); // SELECT * FROM role WHERE name LIKE '%?%';

    List<Role> findByNameStartingWithAndDescriptionEndingWith(String namePart, String descriptionPart);
    // SELECT * FROM role WHERE name LIKE '?%' AND description LIKE '%?';

    @Query(value = "select role from Role as role where role.name = :roleName")
    List<Role> findByNameWithJpql(@Param("roleName") String name);
    // SELECT * FROM role WHERE name = "JAVA DEVELOPER"

    @Query(value = "select role.name from Role as role")
    List<String> findAllNames();
    // SELECT name FROM role;
}
