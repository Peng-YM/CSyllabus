package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.CourseEdge;
import com.peng1m.springboot.model.CourseTree;
import com.peng1m.springboot.model.School;
import com.peng1m.springboot.repository.EdgeRepository;
import com.peng1m.springboot.service.CourseTreeService;
import com.peng1m.springboot.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("curseTreeService")
public class CourseTreeImpl implements CourseTreeService {
    @Autowired
    private EdgeRepository edgeRepository;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CourseEdge> getCourseEdgeBySchoolID(int school_id) {
        School school = schoolService.findByID(school_id);
        List<CourseEdge> courseEdges = edgeRepository.findByschool(school);
        return courseEdges;
    }

    @Override
    public CourseTree buildCourseTree(int school_id) {
        List<CourseEdge> courseEdges = getCourseEdgeBySchoolID(school_id);
        List<Integer> courses = schoolService.getSchoolCourses(school_id);
        return new CourseTree(courseEdges, courses);
    }

    @Override
    public CourseTree updateCourseTree(CourseTree courseTree, int school_id) {
        deleteCourseTree(school_id);
        addCourseTree(courseTree, school_id);
        return null;
    }

    @Override
    public void deleteBySourceOrTarget(int course_id) {
        String sql = "DELETE FROM `edges` WHERE `source`=? OR `target`=?;";
        jdbcTemplate.update(sql, course_id, course_id);
    }


    @Override
    public void deleteCourseTree(int school_id) {
        School school = schoolService.findByID(school_id);
        edgeRepository.deleteByschool(school);
    }


    @Override
    public void addCourseTree(CourseTree courseTree, int schoolid) {
        School school = schoolService.findByID(schoolid);

        LinkedList<Map<String, Integer>> edges = courseTree.getEdges();
        for (Map edge : edges) {
            addEdge(schoolid, (int) edge.get("source"), (int) edge.get("target"));
        }
    }

    private int addEdge(int school, int source, int target) {
        String sql = "INSERT INTO `edges` (`school`, `source`, `target`) VALUES (?,?,?);";
        return jdbcTemplate.update(sql, school, source, target);
    }

}
