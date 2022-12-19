package kr.co.popoolserver.career.repository;

import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerFileRepository extends JpaRepository<CareerFileEntity, Long> {
    //find
    Optional<CareerFileEntity> findByUserEntity(UserEntity userEntity);

    //exists
    boolean existsByUserEntity(UserEntity userEntity);
}
