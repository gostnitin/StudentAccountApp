package nitin.tech.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "AIT_COURSEs")
public class CourseEntity {
	
	@Id
	private Integer courseId;
	private String courseName;

}
