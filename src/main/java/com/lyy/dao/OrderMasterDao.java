package com.lyy.dao;

import com.lyy.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String>{

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
