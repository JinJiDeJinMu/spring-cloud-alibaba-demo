package com.hjb.domain.PO;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "goods")
public class GoodsPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_sn")
    private String goodSn;

    @Column(name = "name")
    private String goodName;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
