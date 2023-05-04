package nitin.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nitin.tech.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
