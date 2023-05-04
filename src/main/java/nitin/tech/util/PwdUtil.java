package nitin.tech.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtil {
	
	public static String generateRandomPwd()
	{
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random(6, characters );
		
		return pwd;
	}

}
