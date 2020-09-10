package com.hjb.repository;


import com.hjb.domain.PO.GoodsPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsPO, Long> {
}
