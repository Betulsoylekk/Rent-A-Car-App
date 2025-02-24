package dev.service;

import dev.dto.User;
import dev.exception.NoSuchUserException;
import dev.utility.UpdateHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    private final Map<UUID,User> users=new ConcurrentHashMap<>();
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        boolean doesExist = users.values().stream()
                .anyMatch(existingUser -> existingUser.getLicenseNumber().equals(user.getLicenseNumber()));
        if (doesExist) {
            throw new IllegalStateException("User with the same license number already exists");
        }

        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
        return user;
    }


    @Override
    public User getUserProfile(UUID userId) throws NoSuchUserException {
        return Optional.ofNullable(users.get(userId))
                .orElseThrow(NoSuchUserException::new);
    }


    @Override
    public User update(UUID userId,User user) throws NoSuchUserException {
        User existingUser = users.get(userId);
        if (existingUser == null) {
            throw new NoSuchUserException();
        }

        // Use the shared updateField method
        UpdateHelper.updateField(user.getName(), existingUser::setName, existingUser.getName());
        UpdateHelper.updateField(user.getEmail(), existingUser::setEmail, existingUser.getEmail());
        UpdateHelper.updateField(user.getLicenseNumber(), existingUser::setLicenseNumber, existingUser.getLicenseNumber());

        return existingUser;
    }

        }



