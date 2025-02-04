package com.example.booking.service;

import com.example.booking.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

}
