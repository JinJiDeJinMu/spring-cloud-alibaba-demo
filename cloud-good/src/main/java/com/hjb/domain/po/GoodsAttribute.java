package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author jinmu
 * @since 2020-11-25
 */
@Data
@TableName("hu_goods_attribute")
public class GoodsAttribute implements Serializable {

    private static final long serialVersionUID=1L;

     @TableId(value = "id", type = IdType.AUTO)
     private Long id;

      /**
     * 分类ID
     */
      private Long categoryId;

      /**
     * 商品ID
     */
      private Long goodsId;

      /**
     * 属性ID
     */
      private Long attrId;

    /**
     * 例如{"key":"颜色","value":["白色","黑色","绿色"]}
     */
    private String attrValue;

    private String goodsAttrImg;

    private Integer attrSort;

    private LocalDateTime createTime;

}
