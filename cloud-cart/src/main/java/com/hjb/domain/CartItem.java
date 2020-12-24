package com.hjb.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinmu
 * @since 2020-12-09
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_cart_item")
public class CartItem implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private Long goodsId;

    private Long userId;

    private Long skuId;

    private Long brandId;

    private String brandName;

    private String goodsName;

    private String goodsImg;

    private BigDecimal price;

    private Long number;

    private String skuDesc;

    private String skuImg;

    private Integer isSelect;

    private Date createTime;


}
