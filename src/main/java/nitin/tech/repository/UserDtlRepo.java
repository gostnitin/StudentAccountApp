package nitin.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nitin.tech.entity.UserDtlEntity;

public interface UserDtlRepo extends JpaRepository<UserDtlEntity, Integer> {
	
	public UserDtlEntity findByEmail(String email);
	
	public UserDtlEntity findByEmailAndPwd(String email, String pwd);

}
