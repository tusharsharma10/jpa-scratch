package com.main.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer studentId;

	@Column(name = "STUDENT_NAME")
	private String studentName;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GUIDE_ID")
	private Guide guide;

	public Student() {
		super();

	}

	public Student(String studentName, Guide guide) {
		super();
		this.studentName = studentName;
		this.guide = guide;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}
	
	
	@Override
	public boolean equals(Object obj){
		
		if(!(obj instanceof Student)) return false;
		Student other = (Student)obj;
		
		return new EqualsBuilder().append(studentName,other.studentName).isEquals();
		
	}
	
	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(studentName).toHashCode();
	}
}
