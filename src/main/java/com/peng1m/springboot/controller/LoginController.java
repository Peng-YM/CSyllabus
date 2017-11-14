package com.peng1m.springboot.controller;

import com.peng1m.springboot.model.User;
import com.peng1m.springboot.service.impl.UserServiceImpl;
import com.peng1m.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(value = "/api/user")
@RestController
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/login/", method =  RequestMethod.POST)
    public ResponseEntity<?> Login(@RequestBody User user, HttpServletResponse response){
        logger.info("User {} is trying to login", user);
        if (userServiceImpl.verifyUser(user.getName(), user.getPassword())){
            String id = userServiceImpl.findByName(user.getName()).getId() + "";
            Cookie cookie = new Cookie("userId", id);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(30*60); // expired after 30 minutes
            response.addCookie(cookie);
            return new ResponseEntity<String>(id, HttpStatus.OK);
        }
        else {
            logger.info("User name or password are incorrect! User: {}", user.getName());
            return new ResponseEntity(new
                    CustomErrorType("User name or password are incorrect!"), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/registered/", method = RequestMethod.POST)
    public ResponseEntity<?> Registered(@RequestBody User user, HttpServletResponse response, UriComponentsBuilder ucBuilder){
        logger.info("Registered User: {}", user);

        if (userServiceImpl.findByName(user.getName()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to registered. A User with name " +
                    user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        userServiceImpl.addUser(user);

        String id = userServiceImpl.findByName(user.getName()).getId() + "";
        Cookie cookie = new Cookie("userId", id);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*60); // expired after 30 minutes
        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
