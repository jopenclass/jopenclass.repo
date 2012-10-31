package org.jopenclass.form;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author madhumal
 * 
 */

@Entity
@Table(name = "lecturer")
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "first_name")
	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	@Column(name = "last_name")
	@NotEmpty(message = "Last name cannot be empty")
	private String lastName;
	@NotFound(action = NotFoundAction.IGNORE)
	private String address;
	@Column(name = "contact_number")
	@NotFound(action = NotFoundAction.IGNORE)
	private String contactNumber;
	@Email
	@NotFound(action = NotFoundAction.IGNORE)
	private String email;
	@OneToMany(mappedBy = "lecturer")
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Course> courseList = new ArrayList<Course>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
