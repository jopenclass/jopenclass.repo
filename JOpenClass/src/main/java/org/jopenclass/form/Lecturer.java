package org.jopenclass.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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

	@Column(name = "lecturer_info")
	@NotFound(action = NotFoundAction.IGNORE)
	@Lob
	private String lecturerInfo;

	@OneToOne(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL, CascadeType.REMOVE })
	private User user;

	@JoinTable(joinColumns = @JoinColumn(name = "lecturer_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.DELETE })
	private Set<Subject> subjectList = new HashSet<Subject>();
	
	@OneToMany(mappedBy="lecturer")
	@Cascade(value = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.DELETE })
	private Set<Batch> batchList = new HashSet<Batch>();

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

	public String getLecturerInfo() {
		return lecturerInfo;
	}

	public void setLecturerInfo(String lecturerInfo) {
		this.lecturerInfo = lecturerInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(Set<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	@JsonIgnore
	public Set<Batch> getBatchList() {
		return batchList;
	}

	@JsonProperty
	public void setBatchList(Set<Batch> batchList) {
		this.batchList = batchList;
	}


}
