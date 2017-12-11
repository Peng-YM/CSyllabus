package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.School;
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

    public School findByID(int id) {
        return schoolRepository.findById(id);
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

        return null;
    }

    public File getSchoolTree(int id) {
        School school = schoolRepository.findById(id);
        String tree_path = school.getTree_path();
        File tree = new File(tree_path);
        return tree;
    }

    public void deleteSchool(int id) {
        schoolRepository.delete(id);
    }
}
