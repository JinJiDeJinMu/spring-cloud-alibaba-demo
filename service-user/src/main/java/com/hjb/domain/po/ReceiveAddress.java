package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinmu
 * @since 2020-11-30
 */
@Data
@TableName("hu_receive_address")
public class ReceiveAddress implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private Long userId;

      /**
     * 收货人
     */
      private String name;

    private String phone;

    private String postCode;

    private String province;

    private String city;

    private String area;

    private String detailAddress;

    private Integer defaultStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
