package dao;

import java.util.List;

import javax.ejb.Local;

import entities.Phone;
import entities.User;

@Local
public interface PhoneLocal {
	void create(Phone p, int user_id);
	void delete(int id);
	void update(Phone u);
	Phone findById(int id);
	Phone findByImei(String imei);
	List<Phone> findAll();
	List<Phone> getPhonesByUser(User u);
	List<Object[]> nbpositions();
	List<Object[]> nbpositions_per_month(int month);
}
