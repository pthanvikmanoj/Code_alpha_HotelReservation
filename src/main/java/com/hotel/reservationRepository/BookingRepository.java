package com.hotel.reservationRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.reservationEntity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("SELECT COUNT(b) > 0 FROM Booking b " +
		       "WHERE b.room.id = :roomId " +
		       "AND b.CheckInDate < :CheckOutDate " +
		       "AND b.CheckOutDate > :CheckInDate")
		boolean existsOverlappingBooking(
		        @Param("roomId") Long roomId,
		        @Param("CheckInDate") LocalDate CheckInDate,
		        @Param("CheckOutDate") LocalDate CheckOutDate
		);
	 List<Booking> findByCustomerEmailIgnoreCase(String customerEmail);
}
