package com.example.booking.service.impl;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import com.example.booking.reposirory.HotelRepository;
import com.example.booking.reposirory.RoomRepository;
import com.example.booking.service.RoomService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DatabaseRoomService implements RoomService {

    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Комната с таким ID {0} не найдена!", id
        )));
    }

    @Transactional
    @Override
    public Room save(Room room, String hotelName) {
        room.setHotel(hotelRepository.findByName(hotelName)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Отель с таким именем {0} не найден!", hotelName
                ))));

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

//        findById(id);
        roomRepository.deleteById(id);
    }
}
