package nitin.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nitin.tech.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
