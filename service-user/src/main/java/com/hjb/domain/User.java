package com.hjb.domain;

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
@TableName("hu_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private String username;

    private String password;

    private String nickName;

      /**
     * 头像
     */
      private String avater;

    private String phone;

    private String email;

    private LocalDateTime registerTime;

      /**
     * 会员等级
     */
      private Long level;

      /**
     * 积分
     */
      private Long scoure;

      /**
     * 成长
     */
      private String growth;

      /**
     * 账号状态
     */
      private Integer status;

    private String lastLoginIp;

    private LocalDateTime lastLoginTime;

    private LocalDateTime createTime;


}
