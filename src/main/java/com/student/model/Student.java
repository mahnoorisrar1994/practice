package com.student.model;


import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Student {
 
	    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    private String firstName;
	    private String lastName;
	    private String email;
	    
	    
	    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	    @JoinColumn(name = "admission_id")  // Foreign key in the Student table
	    private Admission admission;
	    

		public Student(Long id, String firstName, String lastName, String email, Admission admission) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.admission = admission;
			
		}
		
		public Student() {
	        // Default constructor
	    }

		public Admission getAdmission() {
			return admission;
		}

		public void setAdmission(Admission admission) {
			this.admission = admission;
		
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public int hashCode() {
			return Objects.hash(admission, email, firstName, id, lastName);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Student other = (Student) obj;
			return Objects.equals(admission, other.admission) && Objects.equals(email, other.email)
					&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
					&& Objects.equals(lastName, other.lastName);
		}

		

		
		
		
	    
	   

}
