# 🏨 Hotel Reservation System

## 📌 Project Overview

The Hotel Reservation System is a web-based application developed using Java and Spring Boot. It allows users to view available rooms, make hotel reservations, and view booking details.

The application stores reservation information in a MySQL database and provides a simple, user-friendly interface for managing hotel bookings.

## 🎯 Objectives

- To display hotel room details.
- To allow customers to book hotel rooms.
- To collect customer details and reservation dates.
- To store booking information in a MySQL database.
- To display reservation records.
- To simplify the hotel reservation process.

## 🛠️ Technologies Used

- **Backend:** Java, Spring Boot
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript
- **ORM:** Spring Data JPA, Hibernate
- **API:** REST API
- **IDE:** Eclipse
- **Version Control:** Git and GitHub

## ✨ Features

- View hotel room details.
- Enter customer name and email.
- Select a room for reservation.
- Choose check-in and check-out dates.
- Save booking details to the database.
- View booking records.
- Display reservation status messages.

## 📂 Project Structure

```text
HotelReservation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hotel/
│   │   │       ├── hotelController/
│   │   │       ├── hotelService/
│   │   │       ├── hotelRepository/
│   │   │       └── reservationEntity/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── script.js
│   │       └── application.properties
├── pom.xml
└── README.md