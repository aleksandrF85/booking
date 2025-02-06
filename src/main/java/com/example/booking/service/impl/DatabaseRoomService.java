package com.example.booking.service.impl;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import com.example.booking.repository.RoomRepository;
import com.example.booking.service.HotelService;
import com.example.booking.service.RoomService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DatabaseRoomService implements RoomService {

    private final RoomRepository roomRepository;

    private final HotelService databaseHotelService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Комната с таким ID {0} не найдена!", id
        )));
    }

    @Override
    public Room findByHotelAndNumber(Hotel hotel, int number) {

        return roomRepository.findByHotelAndNumber(hotel, number)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Комната в отеле {0} с номером {1} не найдена!", hotel.getName(), number
        )));         //TODO проверить метод
    }

    @Transactional
    @Override
    public Room save(Room room, String hotelName) {
        room.setHotel(databaseHotelService.findByName(hotelName));

        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {

        Room roomForUpdate = findById(room.getId());

        BeanUtils.copyNonNullProperties(room, roomForUpdate);

        return roomRepository.save(roomForUpdate);
    }

    @Override
    public void deleteById(Long id) {

        findById(id);
        roomRepository.deleteById(id);
    }

    @Override
    public void checkCapacity(Room room, int guestAmount){
        if (room.getMaxCapacity() < guestAmount) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Количество гостей превышает вместимость номера {0}", room.getMaxCapacity()
            ));
        }
    }

    @Override
    public void checkDates(LocalDate checkIn, LocalDate checkOut) {

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Дата выезда раньше, чем заезда");
        }
    }

    @Override
    public void setUnavailableDates(Room room, List<LocalDate> bookingDates){

        var unavailableDates = room.getUnavailableDates();
        for (LocalDate date: bookingDates){
            if (unavailableDates.contains(date)){
                throw new IllegalArgumentException(MessageFormat.format(
                        "Дата {0} не доступна", date
                ));
            }
            unavailableDates.add(date);
        }
        room.setUnavailableDates(unavailableDates);

        update(room);
    }

    @Override
    public void removeBookingDates(Room room, List<LocalDate> bookingDates) {

        var unavailableDates = room.getUnavailableDates();
        for (LocalDate date: bookingDates){

            unavailableDates.remove(date);
        }
        room.setUnavailableDates(unavailableDates);

        update(room);
    }
}
