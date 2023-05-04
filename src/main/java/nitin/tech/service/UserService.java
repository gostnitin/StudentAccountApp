package nitin.tech.service;


import nitin.tech.binding.LoginForm;
import nitin.tech.binding.SignUpForm;
import nitin.tech.binding.UnlockForm;


public interface UserService {
	
	
	public boolean signUp(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);

}
