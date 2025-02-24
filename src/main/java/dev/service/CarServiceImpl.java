package dev.service;

import dev.dto.Car;
import dev.exception.NoSuchCarException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static dev.utility.UpdateHelper.updateField;

@Service
public class CarServiceImpl implements CarService {
    private final Map<UUID, Car> cars = new ConcurrentHashMap<>();

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public Car getById(UUID carId) throws NoSuchCarException {
        Car car = cars.get(carId);
        if (car != null) {
            return car;
        }
        throw new NoSuchCarException();

    }


    @Override
    public Car create(Car car) {
        if (cars.get(car.getId()) != null) {
            throw new IllegalStateException("Car already exists.");
        }

        car.setId(UUID.randomUUID());
        cars.put(car.getId(), car);
        return car;
    }

    @Override
    public Car update(UUID carId, Car car) throws NoSuchCarException {
        try {
            Car existingCar = getById(carId);
            // Generic update logic
            updateField(car.getModel(), existingCar::setModel, existingCar.getModel());
            updateField(car.getBrand(), existingCar::setBrand, existingCar.getBrand());
            updateField(car.getYear(), existingCar::setYear, existingCar.getYear());
            updateField(car.getLicensePlate(), existingCar::setLicensePlate, existingCar.getLicensePlate());
            updateField(car.getDailyRate(), existingCar::setDailyRate, existingCar.getDailyRate());
            updateField(car.getAvailable(), existingCar::setAvailable, existingCar.getAvailable());

            return existingCar;
        } catch (NoSuchCarException e) {
            throw new NoSuchCarException();


        }
    }
}
