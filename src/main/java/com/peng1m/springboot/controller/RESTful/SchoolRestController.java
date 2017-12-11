package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.org.json.JSONObject;
import com.peng1m.springboot.service.SchoolService;
import com.peng1m.springboot.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;

/*
* School API
* */
@RequestMapping("/api/school")
@RestController
public class SchoolRestController {

    private SchoolService schoolService;

    @Autowired
    public SchoolRestController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // It will convert to JSON
    //#1
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Integer> getSchoolsID() {
        return schoolService.getSchoolsID();
    }

    // Just information
    //#2
    @RequestMapping(value = "/{school_id}", method = RequestMethod.GET)
    public SchoolInfo findeById(@PathVariable("school_id") int school_id) {
        School school = schoolService.findByID(school_id);
        return new SchoolInfo(school);
    }

    class SchoolInfo {
        int school_id;
        String website;
        String description;

        public SchoolInfo(School school) {
            this.school_id = school.getSchoolid();
            this.website = school.getWebsite();
            this.description = school.getDescription();
        }
    }

    //#6 GET api/school/{school_id}/courses
    public List<Integer> getSchoolCourses(int school_id){

        return null;
    }
    //#7
    @RequestMapping(value = "/{school_id}/tree", method = RequestMethod.GET)
    public JSONObject getSchoolTree(){

        return null;
    }
}
