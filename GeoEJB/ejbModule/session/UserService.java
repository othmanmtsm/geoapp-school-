package session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dao.UserLocal;
import dao.UserRemote;
import entities.User;

@Stateless(name="USR")
public class UserService implements UserLocal, UserRemote  {
	
	@PersistenceContext(unitName = "GeoEJB")
	private EntityManager em;

	@Override
	public User create(User u) {
		em.persist(u);
		return u;
	}

	@Override
	public void delete(int id) {
		User u = findById(id);
		em.remove(u);
	}

	@Override
	public User findById(int id) {
		User u = em.find(User.class, id);
		if(u == null)
			throw new RuntimeException("User introuvable");
		return u;
	}

	@Override
	public List<User> findAll() {
		Query req = em.createQuery("select u from User u");
		return req.getResultList();
	}

	@Override
	public User findByEmail(String email) {
		User u = null;
		Query req = em.createQuery("select u from User u where email = :mail");
		req.setParameter("mail", email);
		try {
			u = (User) req.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return u;
	}

	@Override
	public void update(User u) {
		User user = findById(u.getId());
		if(user != null) {
			user.setBirthDate(u.getBirthDate());
			user.setEmail(u.getEmail());
			user.setName(u.getName());
			user.setPassword(u.getPassword());
			em.persist(em.contains(user) ? user : em.merge(user));
		}
	}
}
