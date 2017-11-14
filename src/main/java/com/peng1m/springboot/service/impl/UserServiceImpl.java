package com.peng1m.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.peng1m.springboot.model.User;
import com.peng1m.springboot.repository.UserDao;
import com.peng1m.springboot.service.UserService;
import com.peng1m.springboot.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	public List<User> userList(){
		List<User> allUsers = new ArrayList<>();
		for (User u: userDao.findAll()) {
			allUsers.add(u);
		}
		return allUsers;
	}
	
	public User findByName(String name) {
		return userDao.findByName(name);
	}

	public User findById(long id){
		return userDao.findById(id);
	}

	public boolean verifyUser(String name, String password){
		return userDao.findByNameAndPassword(name, password) != null;
	}

	public User addUser(User user){
		return userDao.save(user);
	}

	public void updateUser(User new_user){
		User old_user = userDao.findById(new_user.getId());
		old_user.setEmail(new_user.getEmail());
		old_user.setName(new_user.getName());
		old_user.setPassword(new_user.getPassword());
	}
	
	public void deleteAll(){
		userDao.deleteAll();
	}

	public void deleteById(long id){
		userDao.delete(id);
	}

}
