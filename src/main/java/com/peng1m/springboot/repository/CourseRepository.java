package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findBySchool(School school);

    Course findByCourseid(int courseid);


}
