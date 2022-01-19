package session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dao.PhoneLocal;
import dao.PhoneRemote;
import entities.Phone;
import entities.User;

@Stateless(name="PH")
public class PhoneService implements PhoneLocal, PhoneRemote {
	
	@PersistenceContext(unitName = "GeoEJB")
	private EntityManager em;

	@Override
	public void create(Phone p, int user_id) {
		em.createNativeQuery("insert into phone(imei, user_id) values (?, ?)")
			.setParameter(1, p.getImei())
			.setParameter(2, user_id)
			.executeUpdate();
	}

	@Override
	public void delete(int id) {
		Phone p = findById(id);
		em.remove(p);
	}

	@Override
	public Phone findById(int id) {
		Phone p = em.find(Phone.class, id);
		if(p == null)
			throw new RuntimeException("Phone introuvable");
		return p;
	}

	@Override
	public List<Phone> findAll() {
		Query req = em.createQuery("select p from Phone p");
		return req.getResultList();
	}
	
	@Override
	public List<Phone> getPhonesByUser(User u) {
		Query req = em.createQuery("select p from Phone p where p.user_id=:arg1");
		req.setParameter("arg1", u.getId());
		return req.getResultList();
	}

	@Override
	public Phone findByImei(String imei) {
		Query req = em.createQuery("select p from Phone p where p.imei=:arg1");
		req.setParameter("arg1", imei);
		return (Phone) req.getSingleResult();
	}

	@Override
	public void update(Phone u) {
		Phone p = findById(u.getId());
		if(p != null) {
			p.setImei(u.getImei());
		}
	}

	@Override
	public List<Object[]> nbpositions() {
		List<Object[]> positions = null;
		Query req = em.createQuery("SELECT p.imei, count(p) as nb_pos FROM Phone p JOIN Position pos ON p.id = pos.phone GROUP BY p.imei");
        positions = req.getResultList();
        return positions;
	}

	@Override
	public List<Object[]> nbpositions_per_month(int month) {
		List<Object[]> positions = null;
		Query req = em.createQuery("SELECT p.imei, count(p) as nb_pos FROM Phone p JOIN Position pos ON p.id = pos.phone where month(pos.date)=:arg1 GROUP BY p.imei");
		req.setParameter("arg1", month);
        positions = req.getResultList();
        return positions;
	}
	
	
}
