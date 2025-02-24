package dev.service;

import dev.dto.Booking;
import dev.exception.NoSuchBookingException;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<Booking> getBookings();
    Booking create(Booking booking);
    List<Booking> getUserBookings(UUID userId);
    Booking cancelBooking(UUID bookingId) throws NoSuchBookingException;}

