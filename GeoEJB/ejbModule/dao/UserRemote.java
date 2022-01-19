package dao;

import java.util.List;

import javax.ejb.Remote;

import entities.User;

@Remote
public interface UserRemote {
	User create(User u);
	void delete(int id);
	void update(User u);
	User findById(int id);
	User findByEmail(String email);
	List<User> findAll();
}
