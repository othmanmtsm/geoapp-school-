package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

/**
 * Entity implementation class for Entity: Phone
 *
 */
@Entity
public class Phone implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String imei;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "phone", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Position>  positions;
	
	public Phone() {
		super();
	}   
	
	public Phone(String imei, User user) {
		this.imei = imei;
		this.user = user;
	}
	
	
	
	public Phone(int id, String imei) {
		super();
		this.id = id;
		this.imei = imei;
	}

	public Phone(String imei) {
		this.imei = imei;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	@Override
	public String toString() {
		return "Phone [imei=" + imei + "]";
	}
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
   
}
