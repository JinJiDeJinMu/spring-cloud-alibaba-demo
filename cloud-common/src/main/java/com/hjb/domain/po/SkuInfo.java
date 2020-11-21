package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Data
@TableName("hu_sku_info")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long goodsId;

    private String attrList;

    private String skuImg;

    private String skuDesc;

    private String skuTitle;

    private BigDecimal price;

    private Long mount;

    private Long saleCount;


}
