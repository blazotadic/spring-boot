package com.academy.demo.controller;

import com.academy.demo.dto.RoleDTO;
import com.academy.demo.dto.UserWithDetailDTO;
import com.academy.demo.entity.User;
import com.academy.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserWithDetailDTO> store(@RequestBody UserWithDetailDTO userWithDetailDTO)
    {
        UserWithDetailDTO user = userService.save(userWithDetailDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}/add-role")
    public ResponseEntity<User> updateUserRole(@PathVariable Integer id, @RequestBody RoleDTO role)
    {
        User user = userService.addRole(id, role);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserWithDetailDTO>> all()
    {
        List<UserWithDetailDTO> users = userService.all();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Integer id)
    {
        userService.deactivateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "exists/by-username/{username}")
    public ResponseEntity<Map<String, Boolean>> existsByUsername(@PathVariable String username)
    {
        boolean existsStatus = userService.existsByUsername(username);
        return new ResponseEntity<>(Collections.singletonMap("status", existsStatus), HttpStatus.OK);
    }

}
