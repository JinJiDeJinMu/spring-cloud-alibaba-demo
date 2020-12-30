package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * �˻�ԭ��
 * </p>
 *
 * @author jinmu
 * @since 2020-12-30
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_order_return_reason")
public class OrderReturnReason implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * �˻�ԭ����
     */
      private String name;

      /**
     * ����
     */
      private Integer sort;

      /**
     * ����״̬
     */
      private Boolean status;

      /**
     * create_time
     */
      private Date createTime;


}
