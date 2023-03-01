package kr.co.popoolserver.persistence.repository;

import kr.co.popoolserver.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    //find
    Optional<ProductEntity> findByProductName(String productName);

    //exists
    boolean existsByProductName(String productName);
}
