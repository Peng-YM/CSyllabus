package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.School;
import com.peng1m.springboot.repository.CourseRepository;
import com.peng1m.springboot.repository.SchoolRepository;
import com.peng1m.springboot.repository.UserRepository;
import com.peng1m.springboot.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CourseRepository courseRepository;

    public School findByID(int id) {
        return schoolRepository.findBySchoolid(id);
    }

    public List<School> schoolList() {

        return null;
    }

    // find all school ids
    public List<Integer> getSchoolsID() {
        ArrayList<Integer> school_ids = new ArrayList<>();
        for (School school : schoolRepository.findAll()) {
            school_ids.add(school.getSchoolid());
        }
        return school_ids;
    }

    // find all courses of a given school
    public List<Integer> getSchoolCourses(int school_id) {
        ArrayList<Integer> courses_id = new ArrayList<>();
        for (Course course : courseRepository.findBySchool(schoolRepository.findBySchoolid(school_id))) {
            courses_id.add(course.getCourseid());
        }
        return courses_id;
    }


    public School addSchool(School school) {

        return schoolRepository.save(school);
    }

    //Update visible information
    public School updateSchool(School school, int school_id) {
        School oldSchool = schoolRepository.findBySchoolid(school_id);
        if (oldSchool == null) {
            System.out.println("school does not exist");
            return null;
        }
        if (school.getSchool_name() != null) {
            oldSchool.setSchool_name(school.getSchool_name());
        }
        if (school.getDescription() != null) {
            if (school.getDescription().equals("")) {
                oldSchool.setDescription(null);
            } else
                oldSchool.setDescription(school.getDescription());
        }
        if (school.getWebsite() != null) {
            if (school.getWebsite().equals("")) {
                oldSchool.setWebsite(null);
            } else
                oldSchool.setWebsite(school.getWebsite());
        }
        if (school.getLogo_src() != null) {
            if (school.getLogo_src().equals("")) {
                oldSchool.setLogo_src(null);
            } else
                oldSchool.setLogo_src(school.getLogo_src());
        }


        schoolRepository.save(oldSchool);
        return oldSchool;
    }

    public void deleteSchool(int id) {
        schoolRepository.delete(id);
    }


}
