package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.service.CourseService;
import com.peng1m.springboot.service.FileService;
import org.json.JSONObject;
import com.peng1m.springboot.service.SchoolService;
import com.peng1m.springboot.model.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
* School API
* */
@RequestMapping("/api/school")
@RestController
public class SchoolRestController {
    public static final Logger logger = LoggerFactory.getLogger(SchoolRestController.class);
    private SchoolService schoolService;
    private FileService fileService;
    private CourseService courseService;

    @Autowired
    public SchoolRestController(SchoolService schoolService, FileService fileService, CourseService courseService) {
        this.schoolService = schoolService;
        this.fileService = fileService;
        this.courseService = courseService;
    }

    // It will convert to JSON
    //#1 GET api/school
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Integer> getSchoolsID() {
        return schoolService.getSchoolsID();
    }

    // Just information
    //#2 GET api/school/{school_id}
    @RequestMapping(value = "/{school_id}", method = RequestMethod.GET)
    public School findeById(@PathVariable("school_id") int school_id) {
        School school = schoolService.findByID(school_id);
        return school;
    }

    //#3 POST /api/school + json
    @RequestMapping(value = "", method = RequestMethod.POST)
    public School addSchool(@RequestBody School school) {
        logger.info("Add School: {}", school);
        return schoolService.addSchool(school);
    }

    //#4 PUT /api/school + json
    @RequestMapping(value = "/{school_id}", method = RequestMethod.PUT)
    public School updateSchool(@RequestBody School school, @PathVariable("school_id") int school_id) {
        logger.info("Add School: {}", school);
        return schoolService.updateSchool(school, school_id);
    }

    //#5 DELETE /api/school + json
    // json must contains school ID
    @RequestMapping(value = "/{school_id}", method = RequestMethod.DELETE)
    public void deleteSchool(@PathVariable("school_id") int school_id) {
        //delete all courses of the school
        List<Integer> school_courses = schoolService.getSchoolCourses(school_id);
        for (Integer course : school_courses) {
            fileService.deleteSyllabusByCourseID(course);
            courseService.deleteCourse(course);
        }
        //delete school
        schoolService.deleteSchool(school_id);
    }

    //#6 GET api/school/{school_id}/courses
    @GetMapping(value = "/{school_id}/courses")
    public List<Integer> getSchoolCourses(@PathVariable("school_id") int school_id) {
        return schoolService.getSchoolCourses(school_id);
    }

    //#7 GET api/school/{schoold_id}/tree
    @RequestMapping(value = "/{school_id}/tree", method = RequestMethod.GET)
    public String getSchoolTree(@PathVariable("school_id") int school_id) {
        School school = schoolService.findByID(school_id);
        String path = school.getTree_path();
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //#8 POST /api/school/{school_id}/tree + json

    //#9 PUT /api/school/{school_id}/tree + json

    //#10 DELETE /api/school/{school_id}/tree

}
