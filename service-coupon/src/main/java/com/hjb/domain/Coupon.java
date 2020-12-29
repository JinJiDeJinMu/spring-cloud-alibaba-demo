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
 * @since 2020-12-26
 */
@Data
@TableName("hu_coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private String couponName;

    private String couponImg;

      /**
     * 0:满减，1折扣，2立减
     */
      private Integer couponType;

      /**
     * 金额
     */
      private BigDecimal amount;

      /**
     * 使用条件
     */
      private BigDecimal pointAmount;

    private String code;

      /**
     * 0:全场，1:指定分类，2：指定商品
     */
      private Integer useType;

      /**
     * 每人限领个数
     */
      private Integer limitCount;

      /**
     * 优惠券总数
     */
      private Integer publishCount;

      /**
     * 使用数
     */
      private Integer useCount;

      /**
     * 领取数
     */
      private Integer receiveCount;

      private Integer timeDay;

    /**
     * 优惠券生效时间0，开始到结束，1领取多少天后
     */
    private Integer timeType;

    private Date startTime;

    private Date endTime;

      /**
     * 用户领取等级，0:都可以，
     */
      private Integer userLevel;

      /**
     * 0:正常，1:失效
     */
      private Integer status;

      /**
     * 备注
     */
      private String remark;

    private Date createdTime;

    private Long createdUserId;

    private String createdUserName;


}
