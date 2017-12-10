package com.peng1m.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.peng1m.springboot.model.Role;
import com.peng1m.springboot.model.User;
import com.peng1m.springboot.repository.RoleRepository;
import com.peng1m.springboot.repository.UserRepository;
import com.peng1m.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<User> userList() {
        List<User> allUsers = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            allUsers.add(u);
        }
        return allUsers;
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public boolean verifyUser(String name, String password) {
        User user = userRepository.findByName(name);
        return (user != null &&
                bCryptPasswordEncoder.matches(password, user.getPassword()));
    }

    public User addUser(User user) {
        // only store encode password to database
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // set role
        Role userRole = roleRepository.findByName("user");
        user.setRole(userRole);
        user.setSchool(null);
        return userRepository.save(user);
    }

    public void updateUser(User new_user) {
        User old_user = userRepository.findById(new_user.getId());
        old_user.setEmail(new_user.getEmail());
        old_user.setName(new_user.getName());
        old_user.setPassword(new_user.getPassword());
        old_user.setSchool(new_user.getSchool());
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(int id) {
        userRepository.delete(id);
    }


}
