package kr.co.popoolserver.user.repository;

import kr.co.popoolserver.user.domain.entity.CorporateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<CorporateEntity, Long> {
}
