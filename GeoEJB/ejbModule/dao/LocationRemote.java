package dao;

import java.util.Date;

import javax.ejb.Remote;

import entities.Position;

@Remote
public interface LocationRemote {
	void create(String date, double latitude, double longitude, int phone_id);
}
