package dev.service;
import dev.dto.Booking;
import dev.exception.NoSuchBookingException;
import org.springframework.stereotype.Service;
import dev.dto.Car;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Service
public class BookingServiceImpl implements BookingService {
    SearchService searchService;
    CarService carService;
    UserService userService;
    //Concurrent HashMap used to prevent conflicts.
    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();


    @Override
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings.values());
    }

    @Override
    public Booking create(Booking booking) {
        userService.getUserProfile(booking.getUserId()); // Check if the user exists
        carService.getById(booking.getCarId()); // Check if the car exists

        if (!searchService.isCarAvailableForDates(booking.getCarId(), booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalStateException("The car is not available for the selected date range.");
        }

        booking.setId(UUID.randomUUID()); // Generate a new ID for the booking
        bookings.put(booking.getId(), booking); // Use booking.getId() instead of booking.getUserId()
        return booking;
    }





    @Override
    public List<Booking> getUserBookings(UUID userId) {
        return bookings.values().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }




    @Override
    public Booking cancelBooking(UUID bookingId) throws NoSuchBookingException {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            booking.setStatus("cancelled");
            UUID bookedCarId=booking.getCarId();
            Car car=carService.getById(bookedCarId);
            car.setAvailable(true);

            return booking;
        }
        throw new NoSuchBookingException();
    }
}

