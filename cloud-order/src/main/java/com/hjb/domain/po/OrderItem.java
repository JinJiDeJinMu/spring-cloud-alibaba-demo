package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@Data
@TableName("hu_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * order_id
     */
      private Long orderId;

      /**
     * order_sn
     */
      private String orderSn;

      /**
     * spu_id
     */
      private Long goodsId;

      /**
     * spu_name
     */
      private String goodsName;

      /**
     * spu_pic
     */
      private String goodsPic;

      /**
     * 品牌
     */
      private Long brandId;

      /**
     * 分类id
     */
      private Long categoryId;

      /**
     * 商品sku编号
     */
      private Long skuId;

      /**
     * 商品sku名字
     */
      private String skuName;

      /**
     * 商品sku图片
     */
      private String skuPic;

      /**
     * 商品sku价格
     */
      private BigDecimal skuPrice;

      /**
     * 商品购买的数量
     */
      private Integer skuQuantity;

      /**
     * 商品销售属性组合（JSON）
     */
      private String skuAttrsVals;

      /**
     * 商品促销分解金额
     */
      private BigDecimal promotionAmount;

      /**
     * 优惠券优惠分解金额
     */
      private BigDecimal couponAmount;

      /**
     * 积分优惠分解金额
     */
      private BigDecimal integrationAmount;

      /**
     * 该商品经过优惠后的分解金额
     */
      private BigDecimal realAmount;

      /**
     * 赠送积分
     */
      private Integer giftIntegration;

      /**
     * 赠送成长值
     */
      private Integer giftGrowth;


}
