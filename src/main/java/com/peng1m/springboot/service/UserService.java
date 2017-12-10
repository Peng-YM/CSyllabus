package com.peng1m.springboot.service;

import com.peng1m.springboot.model.User;

import java.util.List;

public interface UserService {
    List<User> userList();

    User findById(int id);

    User findByName(String name);

    boolean verifyUser(String name, String password);

    User addUser(User user);

    void updateUser(User user);

    void deleteById(int id);

    void deleteAll();
}
