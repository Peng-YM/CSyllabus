package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.CourseEdge;
import com.peng1m.springboot.model.CourseTree;
import com.peng1m.springboot.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EdgeRepository extends CrudRepository<CourseEdge, Integer> {

    List<CourseEdge> findByEdgeid(int id);

    List<CourseEdge> findByschool(School school);

    //worked, but why?
    @Transactional
    void deleteByschool(School school);


}
