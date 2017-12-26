package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.Course;
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

    @Override
    public List<Integer> getCoursesID() {
        ArrayList<Integer> courses_ids = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courses_ids.add(course.getCourseid());
        }
        return courses_ids;
    }

    @Override
    public Course getCourseByID(int course_id) {
        Course course = courseRepository.findByCourseid(course_id);
        return course;
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course, int course_id) {
        Course oldCourse = courseRepository.findByCourseid(course_id);
        if (oldCourse == null) {
            System.out.println("Course not exist!");
            return null;
        }
        if (course.getCourse_name() != null) {
            oldCourse.setCourse_name(course.getCourse_name());
        }
        if (course.getAuthor() != null) {
            oldCourse.setAuthor(course.getAuthor());
        }
        if (course.getCourse_code() != null) {
            oldCourse.setCourse_code(course.getCourse_code());
        }
        if (course.getDescription() != null) {
            oldCourse.setDescription(course.getDescription());
        }
        if (course.getProfessor() != null) {
            oldCourse.setProfessor(course.getProfessor());
        }
        oldCourse.setLast_modify(course.getLast_modify());
        return courseRepository.save(oldCourse);
    }

    @Override
    public void deleteCourse(int course_id) {
        courseRepository.delete(course_id);
    }


}
