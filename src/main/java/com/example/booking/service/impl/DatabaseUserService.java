package com.example.booking.service.impl;

import com.example.booking.model.User;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.UserService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с таким ID {0} не найден!", id
                )));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с таким именем {0} не найден!", username
                )));
    }

    @Override
    public User save(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Пользователь с таким логином {0} уже зарегистрирован!", user.getUsername()
            ));
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Пользователь с таким Email {0} уже зарегистрирован!", user.getEmail()
            ));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User userForUpdate = findById(user.getId());

        BeanUtils.copyNonNullProperties(user, userForUpdate);
        return userRepository.save(userForUpdate);
    }

    @Override
    public void deleteById(Long id) {

//        findById(id);
        userRepository.deleteById(id);
    }
}
