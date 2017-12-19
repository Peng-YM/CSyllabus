package com.peng1m.springboot.repository;

import com.peng1m.springboot.model.School;
import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public interface SchoolRepository extends CrudRepository<School, Integer> {

    public School findBySchoolid(int id);

}