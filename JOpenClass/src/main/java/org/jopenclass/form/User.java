package org.jopenclass.form;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	@NotEmpty(message="name field cannot be empty")
	private String name;
	@NotEmpty(message="education field cannot be empty")
	private String education;
	// Getter and Setter are omitted for making the code short
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
}
