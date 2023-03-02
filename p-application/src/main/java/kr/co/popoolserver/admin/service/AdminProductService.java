package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.dtos.request.CreateProduct;
import kr.co.popoolserver.dtos.request.UpdateProduct;
import kr.co.popoolserver.persistence.entitiy.AdminEntity;
import kr.co.popoolserver.persistence.entity.ProductEntity;
import kr.co.popoolserver.enums.AdminType;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.exception.UserDefineException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService implements AdminCommonService{

    private final ProductRepository productRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Create Product Service
     * @param createProduct : product info
     * @exception DuplicatedException : PRODUCT NAME CHECK
     * @exception UserDefineException : ADMIN ROLE CHECK
     */
    @Transactional
    public void createProduct(CreateProduct.CREATE_PRODUCT createProduct){
        final AdminEntity adminEntity = AdminThreadLocal.get();

        checkAdminRole(adminEntity.getUserRole());
        checkProductName(createProduct.getProductName());

        final ProductEntity productEntity = ProductEntity.of(createProduct);

        productRepository.save(productEntity);
    }

    /**
     * Update Product Info Service
     * @param updateProduct product info
     * @exception UserDefineException : ADMIN ROLE CHECK
     * @exception BusinessLogicException : PW CHECK
     * @exception BusinessLogicException : Product Name Check
     */
    @Transactional
    public void updateProduct(UpdateProduct.UPDATE_PRODUCT updateProduct){
        final AdminEntity adminEntity = AdminThreadLocal.get();

        checkEncodePassword(updateProduct.getOriginalPassword(), adminEntity.getPassword(), passwordEncoder);
        checkAdminRole(adminEntity.getUserRole());

        ProductEntity productEntity = productRepository.findByProductName(updateProduct.getOriginalProductName())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_PRODUCT_NAME));

        productEntity.updateProduct(updateProduct);
        productRepository.save(productEntity);
    }

    /**
     * Delete Product Service
     * @param deleteProduct product info
     * @exception UserDefineException : ADMIN ROLE CHECK
     * @exception BusinessLogicException : PW CHECK
     * @exception BusinessLogicException : Product Name Check
     */
    @Transactional
    public void deleteProduct(UpdateProduct.DELETE_PRODUCT deleteProduct){
        final AdminEntity adminEntity = AdminThreadLocal.get();

        checkEncodePassword(deleteProduct.getOriginalPassword(), adminEntity.getPassword(), passwordEncoder);
        checkAdminRole(adminEntity.getUserRole());

        final ProductEntity productEntity = productRepository.findByProductName(deleteProduct.getProductName())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_PRODUCT_NAME));

        productRepository.delete(productEntity);
    }

    private void checkProductName(String productName){
        if(productRepository.existsByProductName(productName)){
            throw new DuplicatedException(ErrorCode.DUPLICATED_PRODUCT_NAME);
        }
    }

    @Override
    public Boolean canHandle(AdminType adminType) {
        return adminType.equals(AdminType.PRODUCT);
    }
}
