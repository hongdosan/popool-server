package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.dtos.response.ResponseProduct;
import kr.co.popoolserver.persistence.entity.ProductEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Product All Get Service
     * @return List<Product.READ>
     */
    public List<ResponseProduct.READ_PRODUCT> getProducts(){
        return productRepository.findAll().stream()
                .map(ProductEntity::toDto).collect(Collectors.toList());
    }

    public ResponseProduct.READ_PRODUCT_DETAIL getProduct(String productName){
        return ProductEntity.toDetailDto(productRepository.findByProductName(productName)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_PRODUCT_NAME)));
    }
}
