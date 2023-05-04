package nitin.tech.service;

import java.util.List;

import nitin.tech.binding.DashboardResponse;
import nitin.tech.binding.EnqSearchCriteria;
import nitin.tech.binding.EnquiryForm;
import nitin.tech.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public List<String> getCourseName();
	
	public List<String> getEnqStatus();
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public boolean saveEnquiry (EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquiries();

	public List<StudentEnqEntity> getFilteredEnquiries(EnqSearchCriteria criteria, Integer userId);
	
	

}
