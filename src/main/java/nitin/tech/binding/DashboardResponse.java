package nitin.tech.binding;

import lombok.Data;

@Data
public class DashboardResponse {
	
	private Integer totalEnquiries;
	private Integer enrolledCnt;
	private Integer lostCnt;

}
