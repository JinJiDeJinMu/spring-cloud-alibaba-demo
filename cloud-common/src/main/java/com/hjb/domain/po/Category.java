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
 * 商品三级分类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_category")
public class Category implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 分类id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 分类名称
     */
      private String name;

      /**
     * 父分类id
     */
      private Long parentCid;

      /**
     * 层级
     */
      private Integer catLevel;

      /**
     * 是否显示[0-不显示，1显示]
     */
      private Integer showStatus;

      /**
     * 排序
     */
      private Integer sort;

      /**
     * 图标地址
     */
      private String icon;

      /**
     * 计量单位
     */
      private String productUnit;

      /**
     * 商品数量
     */
      private Integer productCount;


}
