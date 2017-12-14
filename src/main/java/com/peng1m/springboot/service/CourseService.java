package com.peng1m.springboot.service;

import com.peng1m.springboot.model.Course;

import java.util.List;

public interface CourseService {
    List<Integer> getCoursesID();

    Course getCourseByID(int course_id);

    Course addCourse(Course course);

    Course updateCourse(Course course, int course_id);

    void deleteCourse(int course_id);
}