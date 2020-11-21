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
 * 
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_brand")
public class Brand implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 品牌id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 品牌名
     */
      private String name;

      /**
     * 品牌logo地址
     */
      private String logo;

      /**
     * 介绍
     */
      private String descript;

      /**
     * 显示状态[0-不显示；1-显示]
     */
      private Integer isShow;

      /**
     * 排序
     */
      private Integer sort;

    private String address;

    private String brandImg;

    private Date createdTime;


}
