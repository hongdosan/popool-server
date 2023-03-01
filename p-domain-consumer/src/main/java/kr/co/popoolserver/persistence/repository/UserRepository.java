package kr.co.popoolserver.persistence.repository;

import kr.co.popoolserver.persistence.entity.UserEntity;
import kr.co.popoolserver.persistence.entity.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    //find
    Optional<UserEntity> findByIdentity(String identity);

    //exists
    boolean existsByIdentity(String identity);
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
    boolean existsByEmail(String email);
}