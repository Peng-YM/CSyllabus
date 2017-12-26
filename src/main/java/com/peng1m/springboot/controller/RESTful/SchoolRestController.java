package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.CourseTree;
import com.peng1m.springboot.service.*;
import org.json.JSONObject;
import com.peng1m.springboot.model.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/*
* School API
* */
@CrossOrigin("*")
@RequestMapping("/api/school")
@RestController
public class SchoolRestController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolRestController.class);
    private SchoolService schoolService;
    private FileService fileService;
    private CourseService courseService;
    private CourseTreeService courseTreeService;
    private StarSchoolService starSchoolService;

    @Autowired
    public SchoolRestController(SchoolService schoolService, FileService fileService, CourseService courseService, CourseTreeService courseTreeService, StarSchoolService starSchoolService) {
        this.schoolService = schoolService;
        this.fileService = fileService;
        this.courseService = courseService;
        this.courseTreeService = courseTreeService;
        this.starSchoolService = starSchoolService;
    }

    // It will convert to JSON
    //#1 GET api/school
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Map getSchoolsID() {
        Map<String, List<Integer>> schoolid = new HashMap<>();
        schoolid.put("school_ids", schoolService.getSchoolsID());
        return schoolid;
    }

    // Just information
    //#2 GET api/school/{schoolid}
    @RequestMapping(value = "/{school_id}", method = RequestMethod.GET)
    public SchoolInfo findeById(@PathVariable("school_id") int school_id) {
        return new SchoolInfo(schoolService.findByID(school_id));
    }

    class SchoolInfo extends School {
        Integer starNum = null;

        SchoolInfo() {
        }

        SchoolInfo(School school) {
            starNum = starSchoolService.getSchoolStar(school.getSchoolid());
            this.setManager(school.manager);
            this.setWebsite(school.getWebsite());
            this.setLogo_src(school.getLogo_src());
            this.setDescription(school.getDescription());
            this.setSchool_name(school.getSchool_name());
        }

        public int getStarNum() {
            return starNum;
        }

    }

    //get school infos
    //POST api/school/multi
    @PostMapping(value = "/multi")
    public List<School> findSchools(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> school_ids = map.get("school_ids");
        List<School> schools = new LinkedList<>();
        for (Integer id : school_ids) {
            schools.add(schoolService.findByID(id));
        }
        return schools;
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
        courseTreeService.deleteCourseTree(school_id);
        starSchoolService.deleteStarSchoolBySchoolid(school_id);
        //delete school
        schoolService.deleteSchool(school_id);
    }

    //#6 GET api/school/{schoolid}/courses
    @GetMapping(value = "/{school_id}/courses")
    public Map getSchoolCourses(@PathVariable("school_id") int school_id) {
        Map<String, List<Integer>> school_courses = new HashMap<>();
        school_courses.put("course_ids", schoolService.getSchoolCourses(school_id));
        return school_courses;
    }

    //#7 GET api/school/{schoold_id}/tree
    @GetMapping(value = "/{school_id}/tree")
    public CourseTree getSchoolTree(@PathVariable("school_id") int school_id) {
        return courseTreeService.buildCourseTree(school_id);
    }

    //#8 POST /api/school/{school_id}/tree + json
    @PostMapping(value = "/{school_id}/tree")
    public void addSchoolTree(@RequestBody CourseTree courseTree, @PathVariable("school_id") int school_id) {
        courseTreeService.updateCourseTree(courseTree, school_id);
    }

    //#9 PUT /api/school/{school_id}/tree + json
    @PutMapping(value = "/{school_id}/tree")
    public void updateSchoolTree(@RequestBody CourseTree courseTree, @PathVariable("school_id") int school_id) {
        courseTreeService.updateCourseTree(courseTree, school_id);
    }

    //#10 DELETE /api/school/{school_id}/tree
    @DeleteMapping(value = "/{school_id}/tree")
    public void deleteSchoolTree(@PathVariable("school_id") int school_id) {
        courseTreeService.deleteCourseTree(school_id);
    }

}
