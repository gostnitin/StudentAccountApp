package nitin.tech.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "AIT_ENQUIRY_STATUS")
public class EnqStatusEntity {
	
	@Id
	private Integer statusId;
	private String statusName;
	
}
