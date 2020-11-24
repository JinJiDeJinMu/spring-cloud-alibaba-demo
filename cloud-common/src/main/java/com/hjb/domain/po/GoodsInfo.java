package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("hu_goods_info")
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String goodName;

    private String goodDesc;

    private String keyword;

    private String goodDetail;

    private String imgUrl;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;

}
