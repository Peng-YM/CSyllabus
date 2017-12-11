package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.School;
import com.peng1m.springboot.repository.CourseRepository;
import com.peng1m.springboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("coursesService")
public class CoursesServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Integer> getCoursesID() {
        ArrayList<Integer> courses_ids = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courses_ids.add(course.getCourseid());
        }
        return courses_ids;
    }
}
