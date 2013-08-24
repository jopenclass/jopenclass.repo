package org.jopenclass.form;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name="userId")
public class Student extends User {

	@NotFound(action=NotFoundAction.IGNORE)
	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
