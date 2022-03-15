package com.academy.demo.repository;

import com.academy.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "select user from User user left join fetch user.roles where user.id = :id")
    Optional<User> findWithRolesById(@Param("id") Integer id);

    @Query(value = "select user " +
            "from User user " +
            "left join fetch user.userDetail " +
            "left join fetch user.roles " +
            "where user.username = :username and user.isActive = 1")
    User findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);
}
