package dev.service;


import dev.dto.Car;
import dev.exception.NoSuchCarException;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getAllCars();
    Car getById(UUID carId) throws NoSuchCarException;
    Car create(Car car);
    Car update(UUID carId, Car car) throws NoSuchCarException;
}
