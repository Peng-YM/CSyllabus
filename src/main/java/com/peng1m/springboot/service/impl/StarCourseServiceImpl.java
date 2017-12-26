package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.StarCourse;
import com.peng1m.springboot.repository.StarCourseRepository;
import com.peng1m.springboot.service.StarCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StarCourseServiceImpl implements StarCourseService {
    @Autowired
    private StarCourseRepository starCourseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // return all course ids
    public List<Integer> getSrarCourses(int userid) {
        List<StarCourse> starcourses = starCourseRepository.findByAccount_Id(userid);
        LinkedList<Integer> course_ids = new LinkedList<>();

        for (StarCourse starCourse : starcourses) {
            course_ids.add(starCourse.getCourseid());
        }
        return course_ids;
    }

    public int putStarCourses(int userid, List<Integer> course_ids) {
        deleteStarCourseByUserid(userid);
        return insertAllStarCourses(userid, course_ids);
    }

    public int insertAllStarCourses(int userid, List<Integer> course_ids) {
        int row_inserted = 0;
        for (Integer course_id : course_ids) {
            int r = insertStarCourse(userid, course_id);
            row_inserted = row_inserted + r;
        }
        return row_inserted;
    }

    public void deleteCourseStar(int userid, List<Integer> course_ids) {
        for (Integer course_id : course_ids) {
            starCourseRepository.deleteByAccount_IdAndCourse_Courseid(userid, course_id);
        }

    }

    @Override
    public int getCourseStarNum(int courseid) {
        List<StarCourse> starCourses = starCourseRepository.findByCourse_Courseid(courseid);
        if (starCourses != null)
            return starCourses.size();
        else return 0;
    }


    public void deleteStarCourseByUserid(int userid) {
        starCourseRepository.deleteByAccount_Id(userid);
    }

    public int insertStarCourse(int userid, int courseid) {
        String sql = "INSERT INTO `starcourses` (`account`, `courseid`) VALUES (?, ?);";
        return jdbcTemplate.update(sql, userid, courseid);
    }

    public void deleteStarCourseByCourseid(int courseid) {
        starCourseRepository.deleteByCourse_Courseid(courseid);
    }

    public void deleteAll() {
        starCourseRepository.deleteAll();
    }

}
