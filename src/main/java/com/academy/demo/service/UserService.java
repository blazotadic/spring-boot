package com.academy.demo.service;

import com.academy.demo.dto.RoleDTO;
import com.academy.demo.dto.UserWithDetailDTO;
import com.academy.demo.entity.Role;
import com.academy.demo.entity.User;
import com.academy.demo.mapper.RoleMapper;
import com.academy.demo.mapper.UserMapper;
import com.academy.demo.repository.UserRepository;
import com.academy.demo.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Storing user with details
     *
     * @param userWithDetailDTO user details data
     * @return persisted UserDetailDTO Object
     */
    public UserWithDetailDTO save(UserWithDetailDTO userWithDetailDTO)
    {
        User user = userMapper.fromUserWithDetailDTO(userWithDetailDTO);
        user.syncDetails();

        userRepository.save(user);
        return userMapper.toUserWithDetailDTO(user);
    }

    /**
     * Register new user
     *
     * @param userCreateDTO user create DTO
     */
    public void register(UserCreateDTO userCreateDTO)
    {
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());

        User user = userMapper.fromUserCreateToEntity(userCreateDTO);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    /**
     * Add new role to existing user
     *
     * @param id user identifier
     * @param roleDTO role data
     * @return update User Object
     */
    public User addRole(Integer id, RoleDTO roleDTO)
    {
        Optional<User> userOptional = userRepository.findWithRolesById(id);
        if (userOptional.isPresent())
        {
            Role role = roleMapper.toEntity(roleDTO);
            User user = userOptional.get();
            user.addRole(role);

            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User with id " + id + " not found!");
    }

    public List<UserWithDetailDTO> all()
    {
        List<User> users = userRepository.findAll();
        return users
            .stream()
            .map(userMapper::toUserWithDetailDTO).collect(Collectors.toList());
    }

    public void deactivateById(Integer id)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            user.setIsActive(false);

            userRepository.save(user);
        }
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
