package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.StarSchool;
import com.peng1m.springboot.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StarSchoolRepository extends CrudRepository<StarSchool, Integer> {
    List<StarSchool> findByAccount_Id(int accountid);

    @Transactional
    void deleteByAccount_Id(int accountid);

    @Transactional
    void deleteByAccount_IdAndSchool_Schoolid(int accountid, int schoolid);

    @Transactional
    void deleteBySchool_Schoolid(int schoolid);

    @Transactional
    void deleteAll();
}
