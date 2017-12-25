package com.peng1m.springboot.service;

import java.util.List;

public interface StarSchoolService {
    List<Integer> getStarSchool(int usreid);

    public int putStarSchools(int userid, List<Integer> school_ids);

    public int inseatAllStarSchools(int userid, List<Integer> school_ids);

    public void deleteSchoolStar(int userid, List<Integer> school_ids);
}
