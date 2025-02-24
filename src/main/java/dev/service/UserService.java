package dev.service;

import dev.dto.User;
import dev.exception.NoSuchUserException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User create(User user);
    User getUserProfile(UUID userId) throws NoSuchUserException;
    User update(UUID userId, User user) throws NoSuchUserException;
}
