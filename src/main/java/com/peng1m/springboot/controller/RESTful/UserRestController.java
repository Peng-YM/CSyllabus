package com.peng1m.springboot.controller.RESTful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.peng1m.springboot.model.User;
import com.peng1m.springboot.service.*;
import com.peng1m.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin("*")
@RequestMapping("/api/user")
@RestController
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);


    private UserService userService;

    private StarSchoolService starSchoolService;

    private StarCourseService starCourseService;

    private CourseService courseService;

    private SchoolService schoolService;

    @Autowired
    public UserRestController(UserService userService, StarCourseService starCourseService, StarSchoolService starSchoolService, CourseService courseService, SchoolService schoolService) {
        this.userService = userService;
        this.starCourseService = starCourseService;
        this.starSchoolService = starSchoolService;
        this.courseService = courseService;
        this.schoolService = schoolService;

        logger.info("init user controller");

    }

    // -------------------Retrieve All Users---------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        logger.info("retrieve all users");
        List<User> users = userService.userList();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // -------------------Create a User-------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.findByName(user.getName()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. A User with name " +
                    user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        if (user.getRole() == null) {
            //default role is "user"

        }
        userService.addUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a User ------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findById(id);

        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    // ------------------- Delete a User-----------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        logger.info("Fetching & Deleting User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        starCourseService.deleteStarCourseByUserid(id);
        starSchoolService.deleteStarSchoolByUserid(id);
        courseService.removeAuthor(id);
        schoolService.removeManager(id);
        userService.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Users-----------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
        starSchoolService.deleteAll();
        starCourseService.deleteAll();
        courseService.removeAllAuthor();
        schoolService.removeAllManager();
        userService.deleteAll();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    //------------------- Get user Stars---------------------------
    @GetMapping(value = "/star/{user_id}")
    public Map<String, List<Integer>> getuserStar(@PathVariable("user_id") int user_id) {
        logger.info("try to get all stars");
        List<Integer> starschools = starSchoolService.getStarSchool(user_id);
        List<Integer> starcourses = starCourseService.getSrarCourses(user_id);
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("school_ids", starschools);
        map.put("course_ids", starcourses);
        System.out.println(map);
        return map;
    }

    //-------------post all user's star-----------------
    @PostMapping(value = "/star/{user_id}")
    public Map<String, List<Integer>> postUserStar(@PathVariable("user_id") int user_id, @RequestBody Map<String, List<Integer>> star_map) {
        List<Integer> course_ids = star_map.get("course_ids");
        List<Integer> school_ids = star_map.get("school_ids");

        if (course_ids != null)
            starCourseService.putStarCourses(user_id, course_ids);
        if (school_ids != null)
            starSchoolService.putStarSchools(user_id, school_ids);
        return getuserStar(user_id);
    }

    //--------------add new star of the user------------
    //return all stars of the user
    @PutMapping(value = "/star/{user_id}")
    public Map<String, List<Integer>> updateUserStar(@PathVariable("user_id") int user_id, @RequestBody Map<String, List<Integer>> star_map) {
        List<Integer> course_ids = star_map.get("course_ids");
        List<Integer> school_ids = star_map.get("school_ids");

        if (school_ids != null)
            starSchoolService.inseatAllStarSchools(user_id, school_ids);
        if (course_ids != null)
            starCourseService.insertAllStarCourses(user_id, course_ids);
        return null;
    }

    //----------------delete these stars----------------
    @DeleteMapping(value = "/star/{user_id}")
    public void deletUserStar(@PathVariable("user_id") int user_id, @RequestBody Map<String, List<Integer>> star_map) {
        List<Integer> course_ids = star_map.get("course_ids");
        List<Integer> school_ids = star_map.get("school_ids");
        if (course_ids != null)
            starCourseService.deleteCourseStar(user_id, course_ids);
        if (school_ids != null)
            starSchoolService.deleteSchoolStar(user_id, school_ids);

    }
}