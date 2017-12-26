package com.peng1m.springboot.service;

import java.util.List;

public interface StarCourseService {
    List<Integer> getSrarCourses(int userid);

    int putStarCourses(int userid, List<Integer> course_ids);

    int insertAllStarCourses(int userid, List<Integer> course_ids);

    void deleteCourseStar(int userid, List<Integer> course_ids);

    void deleteStarCourseByCourseid(int courseid);

    public void deleteAll();

    int getCourseStarNum(int courseid);

    public void deleteStarCourseByUserid(int userid);
}
