package nitin.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nitin.tech.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
