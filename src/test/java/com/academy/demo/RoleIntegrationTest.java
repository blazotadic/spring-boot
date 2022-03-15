package com.academy.demo;

import com.academy.demo.entity.Role;
import com.academy.demo.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class RoleIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleIntegrationTest.class);

    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void insertTest()
    {
        // INSERT INTO role(name, description) VALUES (?, ?);
        Role role = new Role();
        //role.setId(1); // UPDATE role SET name = ?, description = ? WHERE id = 1;
        role.setName("WEB DEVELOPER");
        role.setDescription("Description for WEB DEVELOPER");

        Role insertedRole = roleRepository.save(role); // insert | update
    }

    @Test
    public void findAllTest()
    {
        List<Role> roles = roleRepository.findAll(); // SELECT * FROM role;
        Role role = roles.get(0);
        LOGGER.info(
            "id: {} | name: {} | description: {}",
            role.getId(), role.getName(), role.getDescription()
        );
    }

    @Test
    public void findByIdTest()
    {
        Optional<Role> roleOptional = roleRepository.findById(1); // SELECT * FROM role WHERE id = 1
        if (roleOptional.isPresent())
        {
            Role role = roleOptional.get();
            LOGGER.info("Role: {}", role);
        }
    }

    @Test
    public void deleteByIdTest()
    {
        // roleRepository.delete(role);
        roleRepository.deleteById(29);
        // DELETE FROM role WHERE id = ?;
    }

    @Test
    public void nameEndingWithTest()
    {
        List<Role> roles = roleRepository.findByNameEndingWith("DEVELOPER");
        LOGGER.info("Roles: {}", roles);
    }
}
