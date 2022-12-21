package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.entitiy.AdminEntity;
import kr.co.popoolserver.entity.product.ProductEntity;
import kr.co.popoolserver.entity.product.dto.ProductDto;
import kr.co.popoolserver.enums.AdminServiceName;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.exception.UserDefineException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService implements AdminCommonService{

    private final ProductRepository productRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create Product Service
     * @param create : product info
     * @exception DuplicatedException : PRODUCT NAME CHECK
     * @exception UserDefineException : ADMIN ROLE CHECK
     */
    @Transactional
    public void createProduct(ProductDto.CREATE create){
        checkAdmin();
        checkProductName(create.getProductName());

        final ProductEntity productEntity = ProductEntity.of(create);
        productRepository.save(productEntity);
    }

    /**
     * Update Product Info Service
     * @param update product info
     * @exception UserDefineException : ADMIN ROLE CHECK
     * @exception BusinessLogicException : PW CHECK
     * @exception BusinessLogicException : Product Name Check
     */
    @Transactional
    public void updateProduct(ProductDto.UPDATE update){
        checkAdmin();
        AdminEntity adminEntity = AdminThreadLocal.get();
        checkEncodePassword(update.getOriginalPassword(), adminEntity.getPassword());

        ProductEntity productEntity = productRepository.findByProductName(update.getOriginalProductName())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_PRODUCT_NAME));
        productEntity.updateProduct(update);
        productRepository.save(productEntity);
    }

    /**
     * Delete Product Service
     * @param delete product info
     * @exception UserDefineException : ADMIN ROLE CHECK
     * @exception BusinessLogicException : PW CHECK
     * @exception BusinessLogicException : Product Name Check
     */
    @Transactional
    public void deleteProduct(ProductDto.DELETE delete){
        checkAdmin();
        AdminEntity adminEntity = AdminThreadLocal.get();
        checkEncodePassword(delete.getOriginalPassword(), adminEntity.getPassword());

        ProductEntity productEntity = productRepository.findByProductName(delete.getProductName())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_PRODUCT_NAME));
        productRepository.delete(productEntity);
    }

    /**
     * Product Name Duplicated Check
     * @param productName
     */
    private void checkProductName(String productName){
        if(productRepository.existsByProductName(productName)) throw new DuplicatedException(ErrorCode.DUPLICATED_PRODUCT_NAME);
    }

    /**
     * 관리자 권한 체크
     */
    @Override
    public void checkAdmin() {
        try {
            AdminEntity adminEntity = AdminThreadLocal.get();
            jwtProvider.checkAdminRole(adminEntity.getUserRole());
        }catch (Exception e){
            throw new UserDefineException(ErrorCode.FAIL_USER_ROLE);
        }
    }

    /**
     * Encode PW Check
     * @param password
     * @param encodePassword
     */
    @Override
    public void checkEncodePassword(String password,
                                    String encodePassword) {
        if(!passwordEncoder.matches(password, encodePassword)) throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
    }

    @Override
    public Boolean canHandle(AdminServiceName adminServiceName) {
        return adminServiceName.equals(AdminServiceName.PRODUCT);
    }
}
