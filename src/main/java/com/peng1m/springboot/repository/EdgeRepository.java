package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.CourseEdge;
import com.peng1m.springboot.model.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EdgeRepository extends CrudRepository<CourseEdge, Integer> {
    List<CourseEdge> findByEdgeid(int id);

    List<CourseEdge> findBySchool(School school);
}
