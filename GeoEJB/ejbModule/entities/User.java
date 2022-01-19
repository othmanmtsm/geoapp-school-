package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private String name;

	@Temporal(TemporalType.DATE)
	private Date birthDate;
	private String email;
	private String password;
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Phone>  phones;

	public User() {
		super();
	}
	
	
	public User(String name, Date birthDate, String email, String password) {
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
	}


	public User(int id, String name, Date birthDate, String email, String password) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	public void addPhone(Phone p) {
		phones.add(p);
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", email=" + email + "]";
	}
   
}
