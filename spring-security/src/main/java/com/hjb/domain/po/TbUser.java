package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("tb_user")
public class TbUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 用户名
     */
      private String username;

      /**
     * 密码，加密存储
     */
      private String password;

      /**
     * 注册手机号
     */
      private String phone;

      /**
     * 注册邮箱
     */
      private String email;

    private Date created;

    private Date updated;

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("test"));
    }

}
