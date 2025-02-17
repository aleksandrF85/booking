package com.example.booking.web.controller;

import com.example.booking.mapper.UserMapper;
import com.example.booking.model.RoleType;
import com.example.booking.model.User;
import com.example.booking.service.UserService;
import com.example.booking.web.dto.UpsertUserRequest;
import com.example.booking.web.dto.UserListResponse;
import com.example.booking.web.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService databaseUserService;

    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        databaseUserService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.userToResponse(
                databaseUserService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request,
                                               @RequestParam RoleType role) {
        User newUser = userMapper.requestToUser(request);
        newUser.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(databaseUserService.save(newUser)));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserResponse> update(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody UpsertUserRequest request) {

        Long id = databaseUserService.findByUsername(userDetails.getUsername()).getId();
        User updatedUser = databaseUserService.update(userMapper.requestToUser(id, request));

        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseUserService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
