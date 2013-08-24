package org.jopenclass.form;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.InheritanceType;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author madhumal
 * 
 */
@Entity
@Table(name = "user")
@Inheritance(strategy=InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue
	private long userId;
    
	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	
	@NotEmpty(message = "Last name cannot be empty")
	private String lastName;
	
	@NotEmpty(message = "Address cannot be empty")
	private String address;
	
	@NotEmpty(message = "Contact Number cannot be empty")
	private String contactNumber;
	
	private String password;
	@Column(name = "enabled")
	private boolean enabled;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"))
	@Column(name = "authority")
	@Cascade({CascadeType.ALL,CascadeType.REMOVE})
	private List<String> userRoles = new ArrayList<String>();
	@Email
	@Size(min = 5)
	@Column(unique = true)
	private String email;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> list) {
		this.userRoles = list;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
}
