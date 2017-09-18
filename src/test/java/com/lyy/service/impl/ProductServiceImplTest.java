package com.lyy.service.impl;

import com.lyy.BaseTest;
import com.lyy.dao.ProductInfoDao;
import com.lyy.domain.ProductInfo;
import com.lyy.enums.ProductStatus;
import com.lyy.vo.ProductInfoVo;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by luyuanyuan on 2017/9/18.
 */
public class ProductServiceImplTest extends BaseTest{

    @Autowired
    private ProductInfoDao productInfoDao;
    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findUpAll() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void save() throws Exception {

//        private String productId;
//
//        private String productName;
//
//        private BigDecimal productPrice;
//
//        private Integer productStock;
//
//        private String productDescription;
//
//        private String productIcon;
//
//        private Integer productStatus;
//
//        private Integer categoryType;
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("111");
        productInfo.setProductName("商品名称一");
        productInfo.setProductPrice(new BigDecimal("12.2"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("商品一描述");
        productInfo.setProductIcon("/xxx.jpg");
        productInfo.setProductStatus(ProductStatus.UP.getCode());
        productInfo.setCategoryType(10);

        productInfoDao.save(productInfo);

        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setProductId("112");
        productInfo1.setProductName("商品名称二");
        productInfo1.setProductPrice(new BigDecimal("34.5"));
        productInfo1.setProductStock(100);
        productInfo1.setProductDescription("商品二描述");
        productInfo1.setProductIcon("/xxx.jpg");
        productInfo1.setProductStatus(ProductStatus.UP.getCode());
        productInfo1.setCategoryType(11);

        productInfoDao.save(productInfo1);

    }

    @Test
    public void lyy(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("111");
        productInfo.setProductName("商品名称一");
        productInfo.setProductPrice(new BigDecimal("12.2"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("商品一描述");
        productInfo.setProductIcon("/xxx.jpg");
        productInfo.setProductStatus(ProductStatus.UP.getCode());
        productInfo.setCategoryType(10);

        ProductInfoVo productInfoVo = new ProductInfoVo();
        BeanUtils.copyProperties(productInfo,productInfoVo);

        System.out.println(productInfoVo);


    }


    @Test
    public void increaseStock() throws Exception {

    }

    @Test
    public void decreaseStock() throws Exception {

    }

}