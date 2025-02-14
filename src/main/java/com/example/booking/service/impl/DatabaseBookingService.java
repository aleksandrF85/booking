package com.example.booking.service.impl;

import com.example.booking.model.Booking;
import com.example.booking.model.User;
import com.example.booking.model.event.RoomBookingEvent;
import com.example.booking.repository.BookingRepository;
import com.example.booking.service.BookingService;
import com.example.booking.service.RoomService;
import com.example.booking.service.UserService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class DatabaseBookingService implements BookingService {

    @Value("${app.kafka.roomBookings}")
    private String roomBookingTopic;

    @Autowired
    private KafkaTemplate<String, RoomBookingEvent> kafkaTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService databaseRoomService;

    @Autowired
    private UserService databaseUserService;


    @Override
    public List<Booking> findAll() {

        return bookingRepository.findAll();
    }


    @Override
    public List<Booking> findByUser(User user) {

        return bookingRepository.findByUser(user);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Бронирование с таким ID {0} не найдено!", id
                )));
    }

    @Transactional
    @Override
    public Booking save(Booking booking, String username) {

        var user = databaseUserService.findByUsername(username);
        booking.setUser(user);


        RoomBookingEvent event = new RoomBookingEvent(user.getId(), booking.getRoom().getId(),
                booking.getCheckIn(), booking.getCheckOut(), LocalDateTime.now());

        kafkaTemplate.send(roomBookingTopic, event);

        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public Booking update(Booking booking) {

        Booking bookingForUpdate = findById(booking.getId());

        BeanUtils.copyNonNullProperties(booking, bookingForUpdate);

        return bookingRepository.save(bookingForUpdate);
    }

    @Override
    public void deleteById(Long id) {

        var booking = findById(id);
        var bookingDates = booking.getCheckIn().datesUntil(booking.getCheckOut()).collect(Collectors.toList());

        databaseRoomService.removeBookingDates(booking.getRoom(), bookingDates);

        bookingRepository.deleteById(id);
    }
}
