package com.lyy.service;

import com.lyy.domain.ProductInfo;
import com.lyy.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 鲁源源 on 2017/9/14.
 */
public interface ProductService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll(Integer productStatus);

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
