package com.hotel.reservationEntity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
		
		private String CustomerName;
		private String CustomerEmail;
		private LocalDate CheckInDate;
		private LocalDate CheckOutDate;
		
		
		@ManyToOne
		private Room room;
		
		public Booking() {
			
		}
		
		//Getter and Setter for Customer Id
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		//Getter and Setter for Customer Name
		public String getCustomerName() {
			return CustomerName;
		}
		public void setCustomerName(String CustomerName) {
			this.CustomerName = CustomerName;
		}
		
		//Getter and Setter For Customer Email
		public String getCustomerEmail() {
			return CustomerEmail;
		}
		public void setCustomerEmail(String CustomerEmail) {
			this.CustomerEmail = CustomerEmail;
		}
		
		//Getter and Setter for Check In Date
		public LocalDate getCheckInDate() {
			return CheckInDate;
		}
		public void setCheckInDate(LocalDate CheckInDate) {
			this.CheckInDate = CheckInDate;
		}
		
		//Getter and Setter for Check Out Date
		public LocalDate getCheckOutDate() {
			return CheckOutDate;
		}
		public void setCheckOutDate(LocalDate CheckOutDate) {
			this.CheckOutDate = CheckOutDate;
		}
		
		//Getter and Setter for Room that has been booked by Customer
		public Room getRoom() {
			return room;
		}
		public void setRoom(Room room) {
			this.room = room;
		}

		
}
