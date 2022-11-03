package kr.co.popoolserver.user.repository;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.user.domain.UserEntity;
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
}