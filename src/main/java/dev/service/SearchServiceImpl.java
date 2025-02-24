package dev.service;

import dev.dto.Car;
import dev.exception.InvalidDateRangeException;
import dev.exception.InvalidPriceRangeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    CarService carService;
    BookingService bookingService;

    @Override
    public List<Car> findAvailableCars(LocalDateTime startDate, LocalDateTime endDate) throws InvalidDateRangeException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException();
        }

        // Filter the cars based on bookings and availability
        return carService.getAllCars().stream()
                .filter(car -> isCarAvailableForDates(car.getId(), startDate, endDate)) // Check if the car is available for the given date range
                .collect(Collectors.toList());
    }


    public boolean isCarAvailableForDates(UUID carId, LocalDateTime startDate, LocalDateTime endDate) {
        // Filter out bookings for the car that conflict with the provided date range
        return bookingService.getBookings().stream()
                .filter(booking -> booking.getCarId().equals(carId)) // Make sure the booking is for the current car
                .noneMatch(booking -> booking.getStartDate().isBefore(endDate) && booking.getEndDate().isAfter(startDate)); // No overlapping bookings
    }


    @Override
    public List<Car> searchByPriceRange(Double minimumPrice, Double maximumPrice) throws InvalidPriceRangeException {
        // Validate the price range
        if (minimumPrice == null || maximumPrice == null || minimumPrice < 0 || maximumPrice < 0) {
            throw new InvalidPriceRangeException();
        }

        if (minimumPrice > maximumPrice) {
            throw new InvalidPriceRangeException();
        }

        // Filter cars within the price range
        return carService.getAllCars().stream()
                .filter(car -> car.getDailyRate() >= minimumPrice && car.getDailyRate() <= maximumPrice)
                .collect(Collectors.toList());
    }
}

