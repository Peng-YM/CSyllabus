package com.peng1m.springboot.service;

import com.peng1m.springboot.model.School;

import java.io.File;
import java.util.List;

public interface SchoolService {

    School findByID(int id);

    List<School> schoolList();

    List<Integer> getSchoolsID();

    List<Integer> getSchoolCourses(int school_id);

    File getSchoolTree(int id);

    School addSchool(School school);

    School updateSchool(School school, int school_id);

    void deleteSchool(int id);


}