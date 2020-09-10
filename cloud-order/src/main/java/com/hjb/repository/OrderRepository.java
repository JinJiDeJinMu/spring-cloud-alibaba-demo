package com.hjb.repository;

import com.hjb.domain.PO.OrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderPO,Long> {
}
