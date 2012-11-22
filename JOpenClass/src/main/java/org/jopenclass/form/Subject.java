package org.jopenclass.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author madhumal
 * 
 */
@Entity
@Table(name = "subject")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty
	@Column(name = "subject_name")
	private String subjectName;

	@NotFound(action = NotFoundAction.IGNORE)
	private String grade;
	
	@ManyToMany(mappedBy="subjectList")
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Lecturer> lecturerList = new HashSet<Lecturer>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Set<Lecturer> getLecturerList() {
		return lecturerList;
	}

	public void setLecturerList(Set<Lecturer> lecturerList) {
		this.lecturerList = lecturerList;
	}

}
