package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.CourseEdge;
import com.peng1m.springboot.model.CourseTree;
import com.peng1m.springboot.repository.EdgeRepository;
import com.peng1m.springboot.service.CourseTreeService;
import com.peng1m.springboot.service.SchoolService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CourseTreeServiceImpl implements CourseTreeService {
    private static final Logger logger = LoggerFactory.getLogger(CourseTreeService.class);

    @Autowired
    EdgeRepository edgeRepository;

    @Autowired
    SchoolService schoolService;

    @Override
    public String buildCourseTree(int school_id) {
        CourseTree tree = new CourseTree();
        for(int c: schoolService.getSchoolCourses(school_id)){
            tree.addNode(c);
        }

        for(CourseEdge e: edgeRepository.finBySchool(schoolService.findByID(school_id))){
            tree.addEdge(e.getSource().getCourseid(), e.getTarget().getCourseid());
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(tree);
        }catch (IOException e){
            logger.error("Cannot convert object to json: {}", e);
        }
        return null;
    }
}
