package nitin.tech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nitin.tech.binding.DashboardResponse;
import nitin.tech.binding.EnqSearchCriteria;
import nitin.tech.binding.EnquiryForm;
import nitin.tech.constants.AppConstants;
import nitin.tech.entity.CourseEntity;
import nitin.tech.entity.EnqStatusEntity;
import nitin.tech.entity.StudentEnqEntity;
import nitin.tech.entity.UserDtlEntity;
import nitin.tech.repository.CourseRepo;
import nitin.tech.repository.EnqStatusRepo;
import nitin.tech.repository.StudentEnqRepo;
import nitin.tech.repository.UserDtlRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private UserDtlRepo userDtlRepo;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private CourseRepo coursesRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public List<String> getCourseName() {
		List<CourseEntity> findAll = coursesRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll)
		{
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		
		List<String> statusList = new ArrayList<>();
		
		for(EnqStatusEntity entity : findAll)
		{
			statusList.add(entity.getStatusName());
		}
		return null;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response = new DashboardResponse();
		
		Optional<UserDtlEntity> findById =userDtlRepo.findById(userId);
		
		if (findById.isPresent())
		{
			UserDtlEntity userEntity = findById.get();
			
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			
			Integer totalCnt = enquiries.size();
			
			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("enrolled"))
					.collect(Collectors.toList()).size();
			
			Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("lost"))
					.collect(Collectors.toList()).size();
			
			response.setTotalEnquiries(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
		}
		return response;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		
	    StudentEnqEntity enqEntity = new StudentEnqEntity();
	    BeanUtils.copyProperties(form, enqEntity);
	    
	    Integer userId =(Integer) session.getAttribute(AppConstants.STR_USER_ID);
	    
	    UserDtlEntity userEntity = userDtlRepo.findById(userId).get();
	    enqEntity.setUser(userEntity);
	    
	    enqRepo.save(enqEntity);
		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		Optional<UserDtlEntity> findById = userDtlRepo.findById(userId);
		if (findById.isPresent())
		{
			UserDtlEntity userDtlEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlEntity.getEnquiries();
		return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnquiries(EnqSearchCriteria criteria, Integer userId) {
				
		Optional<UserDtlEntity> findById = userDtlRepo.findById(userId);
		
		if(findById.isPresent())
		{
			UserDtlEntity userDtlEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlEntity.getEnquiries();
			
			if(criteria.getCourseName()!=null && !"".equals(criteria.getCourseName()))
			{
				enquiries = enquiries.stream()
						.filter(e-> e.getCourseName().equals(criteria.getCourseName()))
					    .collect(Collectors.toList());
			}
			
			if(criteria.getClassMode()!=null && !"".equals(criteria.getClassMode()))
			{
				enquiries = enquiries.stream()
						.filter(e-> e.getClassMode().equals(criteria.getClassMode()))
					    .collect(Collectors.toList());
			}
			
			if(criteria.getEnqStatus()!=null && !"".equals(criteria.getEnqStatus()))
			{
				enquiries = enquiries.stream()
						.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
					    .collect(Collectors.toList());
			
		}
		return enquiries;
	}
		return null;
	}

}
