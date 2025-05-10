package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Customer;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.CustomerRepository;
import com.example.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking saveBooking(Booking booking) {
        if (booking.getCustomer() == null || booking.getCustomer().getId() == null) {
            throw new RuntimeException("Customer ID is required!");
        }

        Optional<Customer> customerOpt = customerRepository.findById(booking.getCustomer().getId());
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer with ID " + booking.getCustomer().getId() + " not found");
        }
        booking.setCustomer(customerOpt.get());

        if (booking.getRooms() != null) {
            for (Room room : booking.getRooms()) {
                Room existingRoom = roomRepository.findById(room.getId())
                        .orElseThrow(() -> new RuntimeException("Room not found: " + room.getId()));
                booking.getRooms().add(existingRoom);
            }
        }

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking with ID " + id + " not found"));

        existingBooking.setCheckIn(updatedBooking.getCheckIn());
        existingBooking.setCheckOut(updatedBooking.getCheckOut());
        existingBooking.setStatus(updatedBooking.getStatus());

        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
