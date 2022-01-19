package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Position
 *
 */
@Entity

public class Position implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private double latitude;
	private double longitude;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "phone_id")
	private Phone phone;
	
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	private static final long serialVersionUID = 1L;

	public Position() {
		super();
	}
	
	public Position(double latitude, double longitude, Date date) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}   
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
   
}
