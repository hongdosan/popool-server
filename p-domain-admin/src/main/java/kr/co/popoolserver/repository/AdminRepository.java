package kr.co.popoolserver.repository;

import kr.co.popoolserver.entitiy.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    //find
    Optional<AdminEntity> findByIdentity(String identity);

    //exists
    boolean existsByIdentity(String identity);
}
