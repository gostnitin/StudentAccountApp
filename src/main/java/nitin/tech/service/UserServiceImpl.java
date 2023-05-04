package nitin.tech.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nitin.tech.binding.LoginForm;
import nitin.tech.binding.SignUpForm;
import nitin.tech.binding.UnlockForm;
import nitin.tech.constants.AppConstants;
import nitin.tech.entity.UserDtlEntity;
import nitin.tech.repository.UserDtlRepo;
import nitin.tech.util.EmailUtil;
import nitin.tech.util.PwdUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlRepo userDtlRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm form){
		
		UserDtlEntity entity = userDtlRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		
		if(entity == null)
		{
			return AppConstants.INVALID_CREDENTIALS_MSG;
		}
		if(entity.getAccStatus().equals(AppConstants.STR_LOCKED))
		{
			return "Your Account Locked";
		}
		
		//create session and store user data in session
		session.setAttribute(AppConstants.STR_USER_ID, entity.getUserId());
		
		return AppConstants.STR_SUCCESS;
	}
	
	@Override
	public boolean unlockAccount (UnlockForm form)
	{ 
		UserDtlEntity entity = userDtlRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd()))
		{
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(AppConstants.STR_UNLOCKED);
			userDtlRepo.save(entity);
			return true;
			
		}else {
			return false;
			
		}
		
		
	}

	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlEntity user = userDtlRepo.findByEmail(form.getEmail());
		
		if(user!=null)
		{
			return false;
		}
		
		//TODO copy data from binding obj to entity obj
		UserDtlEntity entity = new UserDtlEntity();
		BeanUtils.copyProperties(form, entity);
		
		// TODO generate random pwd and set to object
		String tempPwd = PwdUtil.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		//insert account status as LOCKED
		entity.setAccStatus(AppConstants.STR_LOCKED);
		
		//insert record
		userDtlRepo.save(entity);
		
		//send email to unlock the account
		String to = form.getEmail();
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;
		
		StringBuffer body = new StringBuffer("");
		
		body.append("<h1> Use below temporary pwd to unlock your account</h>");
		
		body.append("Temporary pwd : "+ tempPwd);
		
		body.append("<br/>");
		body.append("<br/>");
		
		body.append("<a href= \"http://localhost:9090/unlock?email="+to+"\">Click Here To Unlock Your Account</a>");
		
		emailUtil.sendEmail(to, subject, body.toString());
		
		return true;

	}
	
	@Override
	public boolean forgotPwd(String email)
	{
		//check record present in db with given email
		UserDtlEntity entity = userDtlRepo.findByEmail(email);
		
		//if record not available return false
		if(entity == null)
		{
			return false;
		}
		//if record available send pwd to email and return true
		String Subject = AppConstants.RECOVER_PWD_EMAIL_SUBJECT;
		String body = "Your Pwd :: "+ entity.getPwd();
		
		emailUtil.sendEmail(email, Subject, body);
		
		return true;
	}

}


       
