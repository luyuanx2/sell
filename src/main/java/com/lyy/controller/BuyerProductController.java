package com.lyy.controller;

import com.lyy.domain.ProductCategory;
import com.lyy.domain.ProductInfo;
import com.lyy.enums.ProductStatus;
import com.lyy.service.CategoryService;
import com.lyy.service.ProductService;
import com.lyy.utils.ResultVOUtil;
import com.lyy.vo.ProductInfoVo;
import com.lyy.vo.ProductVo;
import com.lyy.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 鲁源源 on 2017/9/15.
 */
@RestController
@RequestMapping("/buyer/product/")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("list")
    public ResultVo list(){

        List<ProductInfo> productInfos = productService.findUpAll(ProductStatus.UP.getCode());
        List<Integer> categoryTypeList  = productInfos.stream()
                .map(x -> x.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : categories){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVo> productInfoVoList  = new ArrayList<>();
            for (ProductInfo productInfo : productInfos){
                if(Objects.equals(productInfo.getCategoryType(), productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }

            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultVOUtil.success(productVoList);
    }
}
