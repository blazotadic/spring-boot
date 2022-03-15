package com.academy.demo.controller;

import com.academy.demo.dto.RoleDTO;
import com.academy.demo.entity.Role;
import com.academy.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> store(@RequestBody RoleDTO roleDTO)
    {
        if (roleDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Role role = roleService.save(roleDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Role> update(@PathVariable Integer id, @RequestBody RoleDTO roleDTO)
    {
        if (roleDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Role> roleOptional = roleService.update(id, roleDTO);
        return roleOptional
            .map(role -> new ResponseEntity<>(role, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Role>> all()
    {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
