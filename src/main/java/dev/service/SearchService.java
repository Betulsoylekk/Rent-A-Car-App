package dev.service;
import dev.dto.Car;
import dev.exception.InvalidDateRangeException;
import dev.exception.InvalidPriceRangeException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SearchService {
    List<Car> findAvailableCars(LocalDateTime startDate, LocalDateTime endDate) throws InvalidDateRangeException;
    List<Car> searchByPriceRange(Double minimumPrice, Double maximumPrice) throws InvalidPriceRangeException;
    boolean isCarAvailableForDates(UUID carId, LocalDateTime startDate, LocalDateTime endDate);
}
