const ROOM_API = "/rooms";
const BOOKING_API = "/bookings";

const roomList = document.getElementById("roomList");
const roomSelect = document.getElementById("roomId");
const bookingForm = document.getElementById("bookingForm");
const bookingButton = document.getElementById("bookingButton");
const message = document.getElementById("message");

let roomsCache = [];


// Read Java object fields even if JSON names use different capitalization.
function getField(object, ...names) {
    for (const name of names) {
        if (object && object[name] !== undefined && object[name] !== null) {
            return object[name];
        }
    }

    return null;
}


function showMessage(text, type) {
    message.textContent = text;
    message.className = "message " + type;
}


function getRoomId(room) {
    return getField(room, "id", "Id", "ID");
}


function getRoomNumber(room) {
    return getField(room, "roomNumber", "RoomNumber", "roomnumber") ?? "N/A";
}


function getRoomType(room) {
    return getField(room, "roomType", "RoomType", "roomtype") ?? "Standard";
}


function getRoomPrice(room) {
    return getField(
        room,
        "pricePerNight",
        "PricePerNight",
        "pricepernight"
    );
}


function isRoomAvailable(room) {
    const value = getField(room, "available", "Available");

    return value === true || String(value).toLowerCase() === "true";
}


function getBookingRoom(booking) {
    return getField(booking, "room", "Room");
}


// Load rooms from Spring Boot
async function loadRooms() {
    roomList.textContent = "Loading rooms...";

    try {
        const response = await fetch(ROOM_API);

        if (!response.ok) {
            throw new Error("Rooms API returned HTTP " + response.status);
        }

        const data = await response.json();

        if (!Array.isArray(data)) {
            throw new Error("Rooms API response is not a JSON array");
        }

        roomsCache = data;

        displayRooms(data);
        populateRoomSelect(data);

    } catch (error) {
        console.error("Room loading error:", error);

        roomList.textContent =
            "Unable to load rooms. Please check the RoomController API path.";
    }
}


// Display room cards
function displayRooms(rooms) {
    roomList.replaceChildren();

    if (rooms.length === 0) {
        roomList.textContent = "No rooms found.";
        return;
    }

    rooms.forEach(room => {
        const id = getRoomId(room);
        const number = getRoomNumber(room);
        const type = getRoomType(room);
        const price = getRoomPrice(room);
        const available = isRoomAvailable(room);

        const card = document.createElement("article");
        card.className = "room-card";

        const top = document.createElement("div");
        top.className = "room-card-top";

        const icon = document.createElement("span");
        icon.className = "room-icon";
        icon.textContent = "⌂";

        const roomId = document.createElement("span");
        roomId.className = "room-id";
        roomId.textContent = "ROOM #" + number;

        top.append(icon, roomId);

        const body = document.createElement("div");
        body.className = "room-card-body";

        const heading = document.createElement("h3");
        heading.textContent = type + " Room";

        const roomDescription = document.createElement("p");
        roomDescription.textContent = "Room Number: " + number;

        const priceElement = document.createElement("div");
        priceElement.className = "room-price";

        const formattedPrice =
            price !== null && Number.isFinite(Number(price))
                ? "₹" + Number(price).toLocaleString("en-IN")
                : "Price unavailable";

        priceElement.textContent = formattedPrice;

        if (price !== null && Number.isFinite(Number(price))) {
            const perNight = document.createElement("small");
            perNight.textContent = " / night";
            priceElement.appendChild(perNight);
        }

        const status = document.createElement("span");
        status.className = available ? "status" : "status unavailable";
        status.textContent = available ? "Available" : "Unavailable";

        const button = document.createElement("button");
        button.className = "room-book-button";
        button.textContent = available ? "Book This Room →" : "Not Available";
        button.disabled = !available;

        button.addEventListener("click", () => {
            roomSelect.value = String(id);

            document.getElementById("booking").scrollIntoView({
                behavior: "smooth"
            });
        });

        body.append(
            heading,
            roomDescription,
            priceElement,
            status,
            button
        );

        card.append(top, body);
        roomList.appendChild(card);
    });
}


// Fill dropdown with rooms
function populateRoomSelect(rooms) {
    roomSelect.replaceChildren();

    const placeholder = document.createElement("option");
    placeholder.value = "";
    placeholder.textContent = "Choose an available room";

    roomSelect.appendChild(placeholder);

    rooms.forEach(room => {
        if (!isRoomAvailable(room)) {
            return;
        }

        const id = getRoomId(room);

        const option = document.createElement("option");
        option.value = id;
        option.textContent =
            "Room " + getRoomNumber(room) +
            " - " + getRoomType(room) +
            " - ₹" + Number(getRoomPrice(room)).toLocaleString("en-IN");

        roomSelect.appendChild(option);
    });
}


// Create booking
bookingForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const CustomerName =
        document.getElementById("customerName").value.trim();

    const CustomerEmail =
        document.getElementById("customerEmail").value.trim();

    const roomId = Number(roomSelect.value);

    const CheckInDate =
        document.getElementById("checkInDate").value;

    const CheckOutDate =
        document.getElementById("checkOutDate").value;

    if (!CustomerName || !CustomerEmail || !roomId ||
        !CheckInDate || !CheckOutDate) {
        showMessage("Please fill in all required fields.", "error");
        return;
    }

    if (CheckOutDate <= CheckInDate) {
        showMessage(
            "Check-out date must be after check-in date.",
            "error"
        );
        return;
    }

    const selectedRoom = roomsCache.find(
        room => Number(getRoomId(room)) === roomId
    );

    if (!selectedRoom || !isRoomAvailable(selectedRoom)) {
        showMessage(
            "Please select an available room.",
            "error"
        );
        return;
    }

    const booking = {
        CustomerName: CustomerName,
        CustomerEmail: CustomerEmail,
        CheckInDate: CheckInDate,
        CheckOutDate: CheckOutDate,
        room: {
            id: roomId
        }
    };

    bookingButton.disabled = true;
    bookingButton.textContent = "Processing Reservation...";

    showMessage("Creating your reservation...", "");

    try {
        const response = await fetch(BOOKING_API, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(booking)
        });

        const responseText = await response.text();

        if (!response.ok) {
            showMessage(
                "Booking failed: " +
                (responseText || "HTTP " + response.status),
                "error"
            );
            return;
        }

        showMessage(
            "Reservation created successfully!",
            "success"
        );

        bookingForm.reset();

        await loadRooms();
        await loadBookings();

    } catch (error) {
        console.error("Booking error:", error);

        showMessage(
            "Unable to connect to the backend. Check the server and API path.",
            "error"
        );

    } finally {
        bookingButton.disabled = false;
        bookingButton.textContent = "Confirm Reservation →";
    }
});


// Load existing bookings
async function loadBookings() {
    const tableBody = document.getElementById("bookingTableBody");

    tableBody.replaceChildren();

    try {
        const response = await fetch(BOOKING_API);

        if (!response.ok) {
            throw new Error("Bookings API returned HTTP " + response.status);
        }

        const data = await response.json();

        if (!Array.isArray(data) || data.length === 0) {
            const row = tableBody.insertRow();
            const cell = row.insertCell();

            cell.colSpan = 5;
            cell.textContent = "No reservations found.";

            return;
        }

        data.forEach(booking => {
            const row = tableBody.insertRow();

            const id = getField(booking, "id", "Id", "ID");
            const name = getField(
                booking,
                "CustomerName",
                "CustomerName",
                "Customername"
            );

            const checkIn = getField(
                booking,
                "CheckInDate",
                "CheckInDate",
                "CheckinDate"
            );

            const checkOut = getField(
                booking,
                "CheckOutDate",
                "CheckOutDate",
                "CheckoutDate"
            );

            const room = getBookingRoom(booking);
            const roomNumber = room ? getRoomNumber(room) : "N/A";     

            [
                id ?? "N/A",
                name ?? "N/A",
                r0oomNumber,
                checkIn ?? "N/A",
                checkOut ?? "N/A"
            ].forEach(value => {
                const cell = row.insertCell();
                cell.textContent = value;
            });
        });

    } catch (error) {
        console.error("Booking list error:", error);

        const row = tableBody.insertRow();
        const cell = row.insertCell();

        cell.colSpan = 5;
        cell.textContent =
            "Unable to load reservations. Please check the Bookings API path.";
    }
}


// Date restrictions
const today = new Date();
const localToday = [
    today.getFullYear(),
    String(today.getMonth() + 1).padStart(2, "0"),
    String(today.getDate()).padStart(2, "0")
].join("-");

const checkInInput = document.getElementById("checkInDate");
const checkOutInput = document.getElementById("checkOutDate");

checkInInput.min = localToday;
checkOutInput.min = localToday;

checkInInput.addEventListener("change", function () {
    checkOutInput.min = this.value;

    if (checkOutInput.value && checkOutInput.value <= this.value) {
        checkOutInput.value = "";
    }
});


// Start page
loadRooms();
loadBookings();