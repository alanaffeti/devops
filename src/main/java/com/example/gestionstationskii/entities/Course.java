package com.example.gestionstationskii.entities;

import java.io.Serializable;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity

public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numCourse;
	int level;
	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;
	@Enumerated(EnumType.STRING)
	Support support;
	Float price;
	int timeSlot;

	private float rating;

	@JsonIgnore
	@OneToMany(mappedBy= "course")
	Set<Registration> registrations;

	public Course(Long numCourse, int level, TypeCourse typeCourse, Support support, Float price, int timeSlot, float rating) {
		this.numCourse = numCourse;
		this.level = level;
		this.typeCourse = typeCourse;
		this.support = support;
		this.price = price;
		this.timeSlot = timeSlot;
		this.rating = rating;
	}

}
