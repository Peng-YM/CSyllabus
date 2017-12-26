package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.StarCourse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StarCourseRepository extends CrudRepository<StarCourse, Integer> {
    List<StarCourse> findByAccount_Id(int id);

    List<StarCourse> findByCourse_Courseid(int courseid);

    @Transactional
    void deleteByAccount_Id(int id);

    @Transactional
    void deleteByAccount_IdAndCourse_Courseid(int userid, int courseid);

    @Transactional
    void deleteByCourse_Courseid(int courseid);

    @Transactional
    void deleteAll();
}
