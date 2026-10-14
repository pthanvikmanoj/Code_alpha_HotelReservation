package com.hotel.reservationService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotel.reservationEntity.Room;
import com.hotel.reservationRepository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	 private RoomRepository roomRepository;
	 
	 public RoomService(RoomRepository roomRrepository) {
		 this.roomRepository = roomRepository;
	 }
	 
	 //For Adding Rooms
	 public Room addRoom(Room room) {
		 return roomRepository.save(room);
	 }
	 
	 //For To show all Rooms
	 public List<Room> getAllRooms(){
		 return roomRepository.findAll();
	 }
	 
	 //For to get Rooms By Id
	 public Room getById(Long id) {
		 return roomRepository.findById(id).orElse(null);
	 }
	 
	 //For to Update the Rooms
	 public Room updateRoom(Long id,Room room) {
		 Room existingRoom = roomRepository.findById(id).orElse(null);
		 if(existingRoom != null) {
			    existingRoom.setRoomNumber(room.getRoomNumber());
	            existingRoom.setRoomType(room.getRoomType());
	            existingRoom.setPricePerNight(room.getPricePerNight());
	            existingRoom.setAvailable(room.getAvailable());
	            
	            return roomRepository.save(existingRoom);
		 }else {
			 return null;
		 }
	 
	 }
	 
	 //To Delete the Rooms
	 public String deleteRoom(Long id) {
		 Room existingRoom = roomRepository.findById(id).orElse(null);
		 if(existingRoom != null) {
			 roomRepository.deleteById(id);
			 return "Room Successfully Removed";
		 }
	 else {
		 return "Room Not Found!";
	 }
	 }
	 }
