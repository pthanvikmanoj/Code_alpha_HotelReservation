package com.hotel.reservationRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.reservationEntity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	
	List<Room> findByRoomTypeIgnoreCase(String roomType);

    List<Room> findByAvailableTrue();
}