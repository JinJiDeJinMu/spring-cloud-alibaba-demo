package com.hjb.domain.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinmu
 * @since 2020-12-26
 */
@Data
public class CouponParam implements Serializable {

    private static final long serialVersionUID=1L;

    @NotNull(message = "优惠券名称不能为空")
    private String couponName;

    @NotNull(message = "优惠券图片不能为空")
    private String couponImg;

      /**
     * 0:满减，1折扣，2立减
     */
      @NotNull(message = "优惠券类型不能为空")
      private Integer couponType;

      /**
     * 金额
     */
      @NotNull(message = "优惠券金额不能为空")
      private BigDecimal amount;

      /**
     * 使用条件
     */
      @NotNull(message = "优惠券使用条件不能为空")
      private BigDecimal pointAmount;


      /**
     * 0:全场，1:指定分类，2：指定商品
     */
      @NotNull(message = "优惠券适用类别不能为空")
      private Integer useType;

      /**
     * 每人限领个数
     */
      @NotNull(message = "优惠券适用类别不能为空")
      private Integer limitCount;

      /**
     * 优惠券总数
     */
      @NotNull(message = "优惠券适用类别不能为空")
      private Integer publishCount;


      private Integer timeDay;

    @NotNull(message = "优惠券时间类别不能为空")
    private Integer timeType;

    private Date startTime;

    private Date endTime;

      /**
     * 用户领取等级，0:都可以，
     */
      @NotNull(message = "优惠券用户等级不能为空")
      private Integer userLevel;

      /**
     * 备注
     */
      private String remark;


}
