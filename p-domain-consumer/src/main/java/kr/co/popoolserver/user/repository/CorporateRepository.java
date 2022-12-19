package kr.co.popoolserver.user.repository;

import kr.co.popoolserver.user.entity.model.PhoneNumber;
import kr.co.popoolserver.user.entity.CorporateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateRepository extends JpaRepository<CorporateEntity, Long> {

    //find
    Optional<CorporateEntity> findByIdentity(String identity);

    //exists
    boolean existsByIdentity(String identity);
    boolean existsByBusinessPhoneNumber(PhoneNumber phoneNumber);
    boolean existsByBusinessEmail(String email);
}
