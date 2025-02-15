package com.example.booking.service.impl;

import com.example.booking.model.event.UserRegistrationEvent;
import com.example.booking.model.Booking;
import com.example.booking.model.User;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.BookingService;
import com.example.booking.service.UserService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DatabaseUserService implements UserService {

    @Value("${app.kafka.userRegistrations}")
    private String userRegistrationsTopic;

    @Autowired
    private KafkaTemplate<String, UserRegistrationEvent> kafkaTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingService databaseBookingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        isUserAlreadyExist(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRegistrationEvent event = new UserRegistrationEvent(userRepository.save(user).getId(), LocalDateTime.now());
        kafkaTemplate.send(userRegistrationsTopic, event);

        return user;
    }

    @Override
    public User update(User user) {
        User userForUpdate = findById(user.getId());

        BeanUtils.copyNonNullProperties(user, userForUpdate);

        isUserAlreadyExist(userForUpdate);

        return userRepository.save(userForUpdate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        for (Booking booking : findById(id).getBookingList()) {
            databaseBookingService.deleteById(booking.getId());
        }

        userRepository.deleteById(id);
    }

    private void isUserAlreadyExist (User user){

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

    }
}
