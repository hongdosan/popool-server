package kr.co.popoolserver.consumer.service.product;

import kr.co.popoolserver.entity.product.ProductEntity;
import kr.co.popoolserver.entity.product.dto.ProductDto;
import kr.co.popoolserver.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Product All Get Service
     * @return List<Product.READ>
     */
    public List<ProductDto.READ> getProducts(){
        List<ProductDto.READ> reads = ProductEntity.of(productRepository.findAll());

        return reads;
    }
}
