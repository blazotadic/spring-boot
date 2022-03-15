package com.academy.demo;

import com.academy.demo.entity.Role;
import com.academy.demo.entity.User;
import com.academy.demo.entity.UserDetail;
import com.academy.demo.repository.RoleRepository;
import com.academy.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class UserIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserIntegrationTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void insertUserWithDetailsTest()
    {
        User user = new User();
        user.setUsername("usr-00232123");
        user.setPassword("usr-pass-00232123");
        user.setCreatedAt(new Date());
        user.setFirstName("Heril1");
        user.setLastName("Muratovic2");

        UserDetail userDetail = new UserDetail();
        userDetail.setPhoneNumber("112223331");
        userDetail.setAge(30);
        userDetail.setAddress("Address-0012");
        userDetail.setUser(user);

        user.setUserDetail(userDetail);

        userRepository.save(user);
    }

    @Test
    public void insertUserRolesWithParentEntityTest()
    {
        User user = new User();
        user.setUsername("test-009");
        user.setPassword("usr-pass-009");
        user.setCreatedAt(new Date());
        user.setFirstName("Heril-009");
        user.setLastName("Muratovic-009");

        Role manager = new Role();
        manager.setName("Manager");
        manager.setDescription("Rola menadzer");

        Role developer = new Role();
        developer.setName("Developer");
        developer.setDescription("Role Developer");

        Set<Role> roles = new HashSet<>();
        roles.add(manager);
        roles.add(developer);

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Test
    //@Transactional
    public void invalidateUserRoles()
    {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent())
        {
            User user = userOptional.get(); // ID: 8
            //Set<Role> roles = user.getRoles(); // SELECT id, name from role JOIN user_role oN .... where user.id = 8
            user.setRoles(null);
            userRepository.save(user); // UPDATE

        }
    }

    @Test
    public void addOneNewRoleToExistingUserTest()
    {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            Role role = roleRepository.findById(2).orElse(null);

            Set<Role> roles = new HashSet<>();
            roles.add(role);

            user.setRoles(roles);

            userRepository.save(user);
        }
    }

    @Test
    @Transactional
    public void addAnotherRoleToExistingUserTest()
    {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            Role role = roleRepository.findById(3).orElse(null); //
            //Role role = roleRepository.getById(3); // proxy -> id: 3 (NO SQL QUERY!)
            user.addRole(role);

            // opcioni save() poziv
            //userRepository.save(user);
        }
    }

    @Test
    @Transactional
    public void removeRoleFromExistingUserTest()
    {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            Role role = roleRepository.findById(3).orElse(null);

            // user.removeRole(role);
            user.removeByRoleId(3);
            userRepository.save(user);
        }
    }
}
