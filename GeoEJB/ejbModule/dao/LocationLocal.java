package dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Position;

@Local
public interface LocationLocal {
	void create(String date, double latitude, double longitude, int phone_id);
	

}
