package com.main.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Guide {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer guideId;

	@Column(name = "GUIDE_NAME")
	private String guideName;

	@OneToMany(mappedBy = "guide", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
	private Set<Student> studentSet;

	public Guide(String guideName) {
		super();
		this.guideName = guideName;
	}

	public Set<Student> getStudentSet() {
		if (this.studentSet == null) {
			this.studentSet = new HashSet<Student>();
		}

		return studentSet;
	}

	public void addToStudentSet(Student student) {
		this.getStudentSet().add(student);
	}

	public Guide() {
		super();

	}

	public Integer getGuideId() {
		return guideId;
	}

	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

}
