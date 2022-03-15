package com.academy.demo.service;

import com.academy.demo.dto.RoleDTO;
import com.academy.demo.entity.Role;
import com.academy.demo.mapper.RoleMapper;
import com.academy.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    /**
     * Storing new role
     *
     * @param roleDTO role data
     * @return persisted Role Object
     */
    public Role save(RoleDTO roleDTO)
    {
        Role role = roleMapper.toEntity(roleDTO);
        return roleRepository.save(role);
    }

    /**
     * Update role data
     *
     * @param id role identifier
     * @param roleDTO role data to be updated
     * @return Optional of Role Object
     */
    public Optional<Role> update(Integer id, RoleDTO roleDTO)
    {
        boolean roleExists = roleRepository.existsById(id);
        return roleExists ? Optional.of(save(roleDTO)) : Optional.empty();
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
