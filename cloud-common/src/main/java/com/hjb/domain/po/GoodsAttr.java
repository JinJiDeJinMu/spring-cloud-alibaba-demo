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
@TableName("hu_goods_attr")
public class GoodsAttr implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long goodsId;

    private Long attrId;

    private String attrName;

    private String attrValue;

    private String goodsAttrImg;

    private Integer attrSort;

    private LocalDateTime createTime;


}
