package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.School;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface SchoolRepository extends CrudRepository<School, Integer> {
    public School findBySchoolid(int id);

}
