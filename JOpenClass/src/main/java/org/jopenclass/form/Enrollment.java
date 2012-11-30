package org.jopenclass.form;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enrollment")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	/*@Column(name = "std_id")
	private long studentId;
	@Column(name = "course_id")
	private long courseId;*/
	@Column(name = "batch_id")
	private long batchId;
	@Column(name = "enrolled_date")
	private Date enrolledDate;
	@ManyToOne
	@JoinColumn(name="std_id")
	private Student student;

	//student enrolls to the batch. not to the subject
	@ManyToOne
	private Batch batch;
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	/*public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}*/
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public Date getEnrolledDate() {
		return enrolledDate;
	}
	public void setEnrolledDate(Date enrolledDate) {
		this.enrolledDate = enrolledDate;
	}

	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
		

}