package com.peng1m.springboot.controller;

import com.peng1m.springboot.model.UserForm;
import com.peng1m.springboot.model.User;
import com.peng1m.springboot.service.SecurityService;
import com.peng1m.springboot.service.UserService;
import com.peng1m.springboot.service.impl.MyUserDetailsService;
import com.peng1m.springboot.util.CookieUtils;
import com.peng1m.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@RestController
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @GetMapping(value = "/login")
    public ModelAndView login(String error, String logout) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userForm", new UserForm());
        if (error != null)
            modelAndView.addObject("error", "Your username and password is invalid");
        if (logout != null)
            modelAndView.addObject("message", "You have been logged out successfully");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value = "/login")
    public ModelAndView userLogin(UserForm userForm, BindingResult bindingResult, HttpServletResponse response) {
        logger.info("User {} is trying to login", userForm.getUsername());
        ModelAndView modelAndView = new ModelAndView();
        if (userService.verifyUser(userForm.getUsername(), userForm.getPassword())) {
            User user = userService.findByName(userForm.getUsername());
            modelAndView.setViewName("home");
            modelAndView.addObject("welcomeMessage",
                    "Welcome to CSyllabus, Current user: "
                            + user.getName() + "\nAuthority: " + user.getRole().getName());
        } else if (userService.findByName(userForm.getUsername()) == null) {
            bindingResult.rejectValue("username", "error.username", "No such user!");
            modelAndView.setViewName("login");
        } else {
            bindingResult.rejectValue("password", "error.password", "Your password is incorrect.");
            modelAndView.setViewName("login");
        }
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        CookieUtils.setSessionCookie(response, "username", "localhost", userForm.getUsername(), 30 * 60);

        return modelAndView;
    }

    @PostMapping(value = "/login/api/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("User {} is trying to login", user.getName());

        if (!userService.verifyUser(user.getName(), user.getPassword())) {
            return new ResponseEntity<Object>(new CustomErrorType("Invalid credential"), HttpStatus.CONFLICT);
        }
        securityService.autoLogin(user.getName(), user.getPassword());
        return new ResponseEntity<Object>(HttpStatus.OK);
    }


    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userForm", new UserForm());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(UserForm userForm,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setName(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());

        User userExists = userService.findByName(userForm.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("username", "error.user",
                    "There is already a user registered with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }


        userService.addUser(user);
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @RequestMapping("/logout")
    public String logout(Model model) {
        return "login";
    }
}
