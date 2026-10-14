package com.hotel.reservationController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.reservationEntity.Booking;
import com.hotel.reservationService.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	

	//To Add Rooms
	
	@PostMapping
	public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
	    try {
	        Booking savedBooking = bookingService.createBooking(booking);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);

	    } catch (IllegalArgumentException e) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(e.getMessage());
	    }
	}
	
	//To Get All Bookings
	@GetMapping
	public List<Booking> getAllBooking(){
		return bookingService.getAllBookings();
	}
	
	//To Update Booking Details
	@PutMapping("/{id}")
	public ResponseEntity<Booking> updateBooking(
	        @PathVariable Long id,
	        @RequestBody Booking booking) {

	    Booking updatedBooking = bookingService.updateBooking(id, booking);
	    return ResponseEntity.ok(updatedBooking);
	}
	
	//To Cancel the Booking
	@DeleteMapping("/{id}")
	public String deleteBooking(@PathVariable Long id) {
		 bookingService.deleteBooking(id);
		 return "Booking SuccessFully Cancelled";
	}
	
	//To get  Bookings by Customer Id
	@GetMapping("/{id}")
	public Booking getById(@PathVariable Long id) {
		return bookingService.getById(id);
	}
	
}
