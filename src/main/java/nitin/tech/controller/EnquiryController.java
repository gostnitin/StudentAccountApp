package nitin.tech.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nitin.tech.binding.DashboardResponse;
import nitin.tech.binding.EnqSearchCriteria;
import nitin.tech.binding.EnquiryForm;
import nitin.tech.constants.AppConstants;
import nitin.tech.entity.StudentEnqEntity;
import nitin.tech.service.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enqService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	public String logout()
	{
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model)
	{
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);
	
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model)
	{
		initForm(model);
		return "add-enquiry";
	}
	
	private void initForm(Model model)
	{
		//get courses for drop down
		List<String> courses = enqService.getCourseName();
		
		//get enquiry status for drop down 
		List<String> enqStatus = enqService.getEnqStatus();
		
		//create binding class obj
		
		EnquiryForm formObj = new EnquiryForm();
		
		model.addAttribute("courseName", courses);
		model.addAttribute("statusName", enqStatus);
		model.addAttribute("formObj", formObj);
	}
	
		@GetMapping("/enquiries")
	public String viewEnquiriesPage(Model model)
	{
		initForm(model);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
				model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
		
		@GetMapping("/filter-enquiries")
		public String viewEnquiries(@RequestParam String cname, 
				@RequestParam String status, 
				@RequestParam String mode, Model model)
		{
			model.addAttribute("searchForm", new EnqSearchCriteria());
			
			EnqSearchCriteria criteria = new EnqSearchCriteria();
			criteria.setCourseName(cname);
			criteria.setClassMode(mode);
			criteria.setEnqStatus(status);
			
//			List<StudentEnqEntity> enquiries = enqService.getFilteredEnquiries(criteria);
//			model.addAttribute("enquiries", enquiries);
			Integer userId = (Integer) session.getAttribute("userId");
			
			List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnquiries(criteria, userId);
			model.addAttribute("enquiries", filteredEnqs);
		
			return "filter-enquiries-page";
		}
		


}
