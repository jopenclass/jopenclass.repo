package org.jopenclass.form;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import javax.persistence.CascadeType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "batch")
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	@Column(name = "batch_name")
	private String batchName;

	private int intake;

	@Column(name = "is_featured", nullable = false, columnDefinition = "boolean default false")
	private boolean isFeatured;

	private double fee;

	@Transient
	private int enrollmentCount;

	@Column(name = "is_daily_batch")
	private int isDailyBatch;

	@Column(name = "commence_date")
	@Temporal(TemporalType.DATE)
	private Date commenceDate;

	@Lob
	@Column(name = "schedule_discription")
	private String scheduleDiscription;

	@Column(name = "status")
	private int status;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "batch_lecturer", joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
	@NotFound(action = NotFoundAction.EXCEPTION)
	private Lecturer lecturer;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "batch_schedule_id"))
	private Collection<BatchSchedule> batchScheduleList = new HashSet<BatchSchedule>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
	private Collection<Enrollment> enrollmentList = new HashSet<Enrollment>();

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "batch_subject", joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private Subject subject;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public int getIntake() {
		return intake;
	}

	public void setIntake(int intake) {
		this.intake = intake;
	}

	public int getIsDailyBatch() {
		return isDailyBatch;
	}

	public void setIsDailyBatch(int isDailyBatch) {
		this.isDailyBatch = isDailyBatch;
	}

	public Date getCommenceDate() {
		return commenceDate;
	}

	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getScheduleDiscription() {
		return scheduleDiscription;
	}

	public void setScheduleDiscription(String scheduleDiscription) {
		this.scheduleDiscription = scheduleDiscription;
	}

	@JsonIgnore
	public Collection<BatchSchedule> getBatchScheduleList() {
		return batchScheduleList;
	}

	@JsonProperty
	public void setBatchScheduleList(Collection<BatchSchedule> batchScheduleList) {
		this.batchScheduleList = batchScheduleList;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	@JsonIgnore
	public Collection<Enrollment> getEnrollmentList() {
		return enrollmentList;
	}

	@JsonProperty
	public void setEnrollmentList(Collection<Enrollment> enrollmentList) {
		this.enrollmentList = enrollmentList;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public int getEnrollmentCount() {
		return enrollmentCount;
	}

	public void setEnrollmentCount(int enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}

	public boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

}