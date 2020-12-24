package com.hjb.domain;

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
 * 权限表
 * </p>
 *
 * @author jinmu
 * @since 2020-12-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 父权限
     */
      private Long parentId;

      /**
     * 权限名称
     */
      private String name;

      /**
     * 权限英文名称
     */
      private String enname;

      /**
     * 授权路径
     */
      private String url;

      /**
     * 备注
     */
      private String description;

    private Date created;

    private Date updated;


}
