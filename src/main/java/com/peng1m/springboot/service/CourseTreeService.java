package com.peng1m.springboot.service;

import com.peng1m.springboot.model.CourseEdge;
import com.peng1m.springboot.model.CourseTree;

import java.util.List;

public interface CourseTreeService {


    List<CourseEdge> getCourseEdgeBySchoolID(int school_id);

    CourseTree buildCourseTree(int school_id);


    CourseTree updateCourseTree(CourseTree courseTree, int school_id);

    void deleteBySourceOrTarget(int course_id);

    void deleteCourseTree(int school_id);

    void addCourseTree(CourseTree courseTree, int school_id);
}
