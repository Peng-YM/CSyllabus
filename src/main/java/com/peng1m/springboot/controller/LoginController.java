package com.peng1m.springboot.controller;

import com.peng1m.springboot.model.User;
import com.peng1m.springboot.service.UserService;
import com.peng1m.springboot.util.CookieUtil;
import com.peng1m.springboot.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String jwtTokenCookieName = "JWT_TOKEN";
    private static final String key = "KEY";

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView userLogin(User user, BindingResult bindingResult, HttpServletResponse response){
        logger.info("User {} is trying to login", user);
        ModelAndView modelAndView = new ModelAndView();
        if (userService.verifyUser(user.getName(), user.getPassword())){
            user = userService.findByName(user.getName());
            modelAndView.setViewName("home");
            modelAndView.addObject("welcomeMessage",
                    "Welcome to CSyllabus, Current user: "
                            + user.getName() + "\nAuthority: " + user.getRole().getName());
            // add token to cookie
            String token = JwtUtil.generateToken(key, user.getName());
            CookieUtil.create(response, jwtTokenCookieName, token,
                    false, 24 * 60 * 60, "localhost");
        }
        else if (userService.findByName(user.getName()) == null){
            bindingResult.rejectValue("name", "error.name", "No such user!");
            modelAndView.setViewName("login");
        }
        else{
            bindingResult.rejectValue("password", "error.password", "Your password is incorrect.");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult){
        logger.info("User: {} is trying to registered", user);
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByName(user.getName());
        if (userExists != null){
            bindingResult.rejectValue("name", "error.user",
                    "There is already a user registered with the name provided");
        }
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        }else {
            userService.addUser(user);
            modelAndView.addObject("successMessage", "User has benn registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        modelAndView.addObject("welcomeMessage", "Welcome to CSyllabus, Current user: "
                + user.getName()  + "\nAuthority: " + user.getRole().getName());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JwtUtil.invalidateRelatedTokens(request);
        CookieUtil.clear(response, jwtTokenCookieName);
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
