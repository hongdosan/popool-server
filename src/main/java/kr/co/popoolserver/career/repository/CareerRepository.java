package kr.co.popoolserver.career.repository;

import kr.co.popoolserver.career.domain.entity.CareerEntity;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {

    //find
    Optional<CareerEntity> findByUserEntity(UserEntity userEntity);
}
