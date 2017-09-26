package com.lyy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyy.enums.ProductStatus;
import com.lyy.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 鲁源源 on 2017/9/14.
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus = ProductStatus.UP.getCode();

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatus getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatus.class);
    }
}
