package kr.co.popoolserver.user.repository;

import kr.co.popoolserver.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    //exists
    boolean existsByIdentity(String identity);
    boolean existsByPhoneNumber(String phoneNumber);
}