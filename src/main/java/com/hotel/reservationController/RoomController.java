package com.hotel.reservationController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.reservationEntity.Room;
import com.hotel.reservationService.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	 private RoomService roomService;
	 
	 public RoomController(RoomService roomService) {
		 this.roomService = roomService;
	 }
	 
	 //To Add the Rooms
	 @PostMapping
	 public Room addRoom(@RequestBody Room room) {
		 return roomService.addRoom(room);
	 }
	 
	 //To Show the All Rooms
	 @GetMapping
	 public List<Room> getAllRooms(){
		 return roomService.getAllRooms();
	 }
	 
	 //To Update Rooms
	 @PutMapping
	 public Room updateRoom(@PathVariable Long id,@RequestBody Room room) {
		 return roomService.updateRoom(id, room);
	 }
	 
	 //To Remove the Rooms that were not available
	 @DeleteMapping
	 public String deleteRoom(@PathVariable Long id) {
		  roomService.deleteRoom(id);
		  return "Room Successfully Removed" ;
	 }
	 	
	 //Get Rooms By Id
	 @GetMapping("/{id}")
	 public Room getById(@PathVariable Long id) {
		 return roomService.getById(id);
	 }
	 
	 
}
