package org.jopenclass.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author madhumal
 * 
 */

@Entity
@Table(name = "lecturer")
@PrimaryKeyJoinColumn(name="userId")
public class Lecturer extends User {

	@Column(name = "lecturer_info")
	@NotFound(action = NotFoundAction.IGNORE)
	@Lob
	private String lecturerInfo;

	@JoinTable(joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.DELETE })
	private Set<Subject> subjectList = new HashSet<Subject>();
	
	@OneToMany(mappedBy="lecturer")
	@Cascade(value = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.DELETE })
	private Set<Batch> batchList = new HashSet<Batch>();

	

	
	public String getLecturerInfo() {
		return lecturerInfo;
	}

	public void setLecturerInfo(String lecturerInfo) {
		this.lecturerInfo = lecturerInfo;
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
