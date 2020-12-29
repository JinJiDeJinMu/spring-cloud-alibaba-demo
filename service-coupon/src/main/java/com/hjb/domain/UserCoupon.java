package com.hjb.domain;

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
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_user_coupon")
public class UserCoupon implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private Long userId;

    private Long couponId;

      /**
     * 数量
     */
      private Integer count;

      /**
     * 领取时间
     */
      private Date receiveTime;

      /**
     * 优惠券状态0,待使用，1已使用，2已失效
     */
      private Integer status;

    private Date createdTime;


}
