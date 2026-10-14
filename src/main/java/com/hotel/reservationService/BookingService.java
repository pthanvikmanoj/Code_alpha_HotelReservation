package com.hotel.reservationService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.hotel.reservationEntity.Booking;
import com.hotel.reservationEntity.Room;
import com.hotel.reservationRepository.BookingRepository;
import com.hotel.reservationRepository.RoomRepository;

@Service
public class BookingService {

	
	 @Autowired
	 private final BookingRepository bookingRepository;
	 private  Booking booking;
	 private final RoomRepository roomRepository;
	 
	 public BookingService(
			 BookingRepository bookingRepository,
			 RoomRepository roomRepository) {
		 this.bookingRepository = bookingRepository;
		 this.roomRepository = roomRepository;
	 }
	 
	 //For Create Booking
	 public Booking createBooking(Booking booking) {

		    if (booking == null) {
		        throw new IllegalArgumentException("Booking request cannot be null");
		    }

		    // Check-in and check-out date validation
		    if (booking.getCheckInDate() == null ||
		        booking.getCheckOutDate() == null) {
		        throw new IllegalArgumentException("Check-in and check-out dates are required");
		    }

		    if (!booking.getCheckOutDate().isAfter(booking.getCheckInDate())) {
		        throw new IllegalArgumentException("Check-out date must be after check-in date");
		    }

		    // Room validation
		    if (booking.getRoom() == null || booking.getRoom().getId() == null) {
		        throw new IllegalArgumentException("Room ID is required");
		    }

		    Room room = roomRepository.findById(booking.getRoom().getId())
		            .orElseThrow(() -> new RuntimeException("Room not found"));

		    // Room availability validation
		    if (room.getAvailable() == null || !room.getAvailable()) {
		        throw new IllegalArgumentException("Room is not available");
		    }

		    booking.setRoom(room);
		    boolean alreadyBooked = bookingRepository.existsOverlappingBooking(
		            room.getId(),
		            booking.getCheckInDate(),
		            booking.getCheckOutDate()
		    );

		    if (alreadyBooked) {
		        throw new IllegalArgumentException(
		                "Room is already booked for the selected dates"
		        );
		    }

		    return bookingRepository.save(booking);
		}	 
	 //For to Get All Bookings
	 public List<Booking> getAllBookings(){
		 return bookingRepository.findAll();
		 }
	 
	 //For to get Rooms by Booking Id
	 public Booking getById(@PathVariable Long id) {
		 return bookingRepository.findById(id).orElse(null);
	 }
	 
	 //For to Update the Booking Details
	 public Booking updateBooking(Long id, Booking updateBooking) {

		    Booking existingBooking = bookingRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Booking not found"));

		    existingBooking.setCustomerName(updateBooking.getCustomerName());
		    existingBooking.setCustomerEmail(updateBooking.getCustomerEmail());
		    existingBooking.setCheckInDate(updateBooking.getCheckInDate());
		    existingBooking.setCheckOutDate(updateBooking.getCheckOutDate());

		    if (updateBooking.getRoom() != null
		            && updateBooking.getRoom().getId() != null) {

		        Room room = roomRepository.findById(updateBooking.getRoom().getId())
		                .orElseThrow(() -> new RuntimeException("Room not found"));

		        existingBooking.setRoom(room);
		    }

		    return bookingRepository.save(existingBooking);
		}
	 
	 //For Delete (Cancelling) the Booking
	 public String deleteBooking(Long id) {
		 Booking existingBooking = bookingRepository.findById(id).orElse(null);
		 if(existingBooking != null) {
			 bookingRepository.deleteById(id);
			 return "Room Successfully Removed";
		 }
	 else {
		 return "Room Not Found!";
	 }
	 }
	 
	 }
	 

