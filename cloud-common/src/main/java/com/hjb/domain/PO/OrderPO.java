package com.hjb.domain.PO;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class OrderPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "good_id")
    private Long goodId;

    @Column(name = "order_sn")
    private String orderSn;

    @Column(name = "mount")
    private Integer mount;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
