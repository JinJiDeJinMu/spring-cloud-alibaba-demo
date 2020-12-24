package com.hjb.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 属性组合json
     */
    private String attrList;

    /**
     * sku规格图
     */
    private String skuImg;

    /**
     * sku描述
     */
    private String skuDesc;

    /**
     * sku标题
     */
    private String skuTitle;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Long mount;

    /**
     * 销售量
     */
    private Long saleCount;


}
