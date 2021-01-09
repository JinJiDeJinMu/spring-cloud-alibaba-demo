package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * �����˻�����
 * </p>
 *
 * @author jinmu
 * @since 2021-01-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_order_return_apply")
public class OrderReturnApply implements Serializable {

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

    private String orderSn;

      /**
     * 0;退款，1退款退货，2换货
     */
      private Integer type;

      private Integer status;

    private Long userId;

      /**
     * 退款金额
     */
      private BigDecimal returnMoney;

      /**
     * 退货原因
     */
      private String returnReason;

      /**
     * 问题描述
     */
      private String description;

      /**
     * 图片描述
     */
      private String descPics;

      /**
     * 退换收货人地址
     */
      private String returnAddress;

      /**
     * 退换收货人
     */
      private String returnName;

      /**
     * 退换收货人电话
     */
      private String returnPhone;

      /**
     * 快递号
     */
      private String expressCode;

      /**
     * 处理时间
     */
      private LocalDateTime handleTime;

      /**
     * 处理记录
     */
      private String handleNote;

      /**
     * 处理人
     */
      private String handleUserId;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
