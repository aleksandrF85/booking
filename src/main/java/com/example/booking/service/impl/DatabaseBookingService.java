package com.example.booking.service.impl;

import com.example.booking.model.Booking;
import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import com.example.booking.model.User;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.RoomRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.BookingService;
import com.example.booking.service.RoomService;
import com.example.booking.service.UserService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseBookingService implements BookingService {

    private final BookingRepository bookingRepository;

    private final RoomService databaseRoomService;

    private final UserService databaseUserService;


    @Override
    public List<Booking> findAll() {

        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findByRoom(Room room) {

        return bookingRepository.findByRoom(room);
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

        booking.setUser(databaseUserService.findByUsername(username));

        return bookingRepository.save(booking);
    }

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
