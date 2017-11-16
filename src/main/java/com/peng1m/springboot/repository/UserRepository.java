package com.peng1m.springboot.repository;


import com.peng1m.springboot.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public User findByName(String username);
    public User findById(long id);
    public User findByNameAndPassword(String name, String password);
}
