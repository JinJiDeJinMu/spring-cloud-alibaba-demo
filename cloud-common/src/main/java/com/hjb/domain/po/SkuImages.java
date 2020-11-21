package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * skuͼƬ
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_sku_images")
public class SkuImages implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * sku_id
     */
      private Long skuId;

      /**
     * ͼƬ��ַ
     */
      private String imgUrl;

      /**
     * ����
     */
      private Integer imgSort;


}
