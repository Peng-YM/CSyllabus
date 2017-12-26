package com.peng1m.springboot.service;

import java.util.List;

public interface StarCourseService {
    List<Integer> getSrarCourses(int userid);

    public int putStarCourses(int userid, List<Integer> course_ids);

    public int insertAllStarCourses(int userid, List<Integer> course_ids);

    public void deleteCourseStar(int userid, List<Integer> course_ids);
}
