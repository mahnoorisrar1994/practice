package com.student.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admission {
	
	    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private LocalDate admissionDate;
	    private String status;
	    
		public Admission(Long id, LocalDate admissionDate, String status) {
			super();
			this.id = id;
			this.admissionDate = admissionDate;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}


		public LocalDate getAdmissionDate() {
			return admissionDate;
		}

		public void setAdmissionDate(LocalDate admissionDate) {
			this.admissionDate = admissionDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public int hashCode() {
			return Objects.hash(admissionDate, id, status);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Admission other = (Admission) obj;
			return Objects.equals(admissionDate, other.admissionDate) && Objects.equals(id, other.id)
					&& Objects.equals(status, other.status);
		}

		
		
	

}
