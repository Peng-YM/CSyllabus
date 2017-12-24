package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.User;
import com.peng1m.springboot.service.SecurityService;
import com.peng1m.springboot.service.UserService;
import com.peng1m.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@Controller
public class LoginRestController {
    private static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value = "/api/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        logger.info("User {} is trying to login", user.getName());

        if (!userService.verifyUser(user.getName(), user.getPassword())) {
            return new ResponseEntity<Object>(new CustomErrorType("Invalid credential"), HttpStatus.CONFLICT);
        }
        securityService.autoLogin(user.getName(), user.getPassword());
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/registration")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("User {} is trying to register", user.getName());

        if (userService.findByName(user.getName()) != null) {
            return new ResponseEntity<Object>(
                    new CustomErrorType(String.format("User Name %s already exists", user.getName())), HttpStatus.CONFLICT);
        }
        securityService.autoLogin(user.getName(), user.getPassword());
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
