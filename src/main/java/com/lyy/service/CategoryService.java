package com.lyy.service;

import com.lyy.domain.ProductCategory;

import java.util.List;

/**
 * Created by 鲁源源 on 2017/9/14.
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
}
