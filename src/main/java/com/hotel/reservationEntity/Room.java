package com.hotel.reservationEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Room {
	   @Id
	   @GeneratedValue
	   private Long id;
	   
	   private String RoomNumber;
	   private String RoomType;
	   private double PricePerNight;
	   private Boolean Available;
	   
	   //Getter and Setter for Room Id
	   public void setId(Long id) {
		   this.id = id;
	   }
	   public Long getId() {
		   return id;
	   }
	   
	   //Getter and Setter for Room Number
	   public void setRoomNumber(String RoomNumber) {
		   this.RoomNumber = RoomNumber;
	   }
	   public String getRoomNumber() {
		   return RoomNumber;
	   }
	   
	   //Getter and Setter for Room Type
	   public void setRoomType(String RoomType) {
		   this.RoomType = RoomType;
	   }
	   public String getRoomType() {
		   return RoomType;
	   }
	   
	   //Getter and Setter for Price for the Room for Night
	   public void setPricePerNight(double PricePerNight) {
		   this.PricePerNight = PricePerNight;
	   }
	   public double getPricePerNight() {
		   return PricePerNight;
	   }
	   
	   //Getter and Setter to Check the Availability of the Rooms
	   public void setAvailable(Boolean Available) {
		   this.Available = Available;
	   }
	   public Boolean getAvailable() {
		   return Available;
	   }
	   
	   
	   
}
