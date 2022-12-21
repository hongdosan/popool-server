package kr.co.popoolserver.repository.career;

import kr.co.popoolserver.entity.career.CareerFileEntity;
import kr.co.popoolserver.entity.user.UserEntity;
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
