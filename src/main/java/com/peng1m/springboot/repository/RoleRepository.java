package com.peng1m.springboot.repository;


import com.peng1m.springboot.model.Role;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RoleRepository extends CrudRepository<Role, Long>{
    public Role findById(long id);
    public Role findByName(String name);
}
