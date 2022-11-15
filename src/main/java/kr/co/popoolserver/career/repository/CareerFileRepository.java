package kr.co.popoolserver.career.repository;

import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerFileRepository extends JpaRepository<CareerFileEntity, Long> {
}
