package session;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.LocationLocal;
import dao.LocationRemote;
import entities.Position;

@Stateless(name="POS")
public class PositionService implements LocationLocal, LocationRemote {
	
	@PersistenceContext(unitName = "GeoEJB")
	private EntityManager em;
	
	@Override
	public void create(String date, double latitude, double longitude, int phone_id) {
		em.createNativeQuery("insert into position (date, latitude, longitude, phone_id) values(?, ?, ?, ?)")
		.setParameter(1, date)
		.setParameter(2, latitude)
		.setParameter(3, longitude)
		.setParameter(4, phone_id)
		.executeUpdate();
	}

}
