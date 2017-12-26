package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.model.StarSchool;
import com.peng1m.springboot.repository.StarSchoolRepository;
import com.peng1m.springboot.service.StarSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StarSchoolServiceImpl implements StarSchoolService {
    @Autowired
    private StarSchoolRepository starSchoolRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Integer> getStarSchool(int usreid) {
        List<StarSchool> starcourses = starSchoolRepository.findByAccount_Id(usreid);
        LinkedList<Integer> school_ids = new LinkedList<>();

        for (StarSchool starSchool : starcourses) {
            school_ids.add(starSchool.getSchoolid());
        }
        return school_ids;
    }

    public int putStarSchools(int userid, List<Integer> school_ids) {
        deleteStarSchoolByUserid(userid);
        return inseatAllStarSchools(userid, school_ids);
    }

    public int inseatAllStarSchools(int userid, List<Integer> school_ids) {
        int row_inserted = 0;
        for (Integer school_id : school_ids) {
            int r = insertStarCourse(userid, school_id);
            row_inserted = row_inserted + r;
        }
        return row_inserted;
    }

    public void deleteSchoolStar(int userid, List<Integer> school_ids) {
        for (Integer school_id : school_ids) {
            starSchoolRepository.deleteByAccount_IdAndSchool_Schoolid(userid, school_id);
        }
    }

    public void deleteStarSchoolByUserid(int userid) {
        starSchoolRepository.deleteByAccount_Id(userid);
    }

    public int insertStarCourse(int userid, int schoolid) {
        String sql = "INSERT INTO `starschools` (`account`, `schoolid`) VALUES (?, ?);";
        return jdbcTemplate.update(sql, userid, schoolid);
    }

    public void deleteStarSchoolBySchoolid(int schoolid) {
        starSchoolRepository.deleteBySchool_Schoolid(schoolid);
    }

    public void deleteAll(){
        starSchoolRepository.deleteAll();
    }
}
