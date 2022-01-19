package dao;

import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface UserLocal {
	User create(User u);
	void delete(int id);
	void update(User u);
	User findById(int id);
	User findByEmail(String email);
	List<User> findAll();
}
