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
 * @since 2020-11-25
 */
@Data
@TableName("hu_goods_info")
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 商品名称
     */
      private String goodsName;

      /**
     * 关键字
     */
      private String keyword;

      /**
     * 商品描述
     */
      private String goodsDesc;

      /**
     * 商品详情介绍（富文本标签）
     */
      private String goodsDetail;

      /**
     * 主图
     */
      private String mainImgUrl;

      /**
     * 轮播图
     */
      private String carouselImgUrl;

      /**
     * 分类
     */
      private Long categoryId;

      private String categoryName;

      /**
     * 品牌
     */
      private Long brandId;

      private String brandName;
      /**
     * 是否上架，0:上架,1:下架
     */
      private Integer isPublish;

      /**
     * 是否新品，0是新品，1:不是
     */
      private Integer isNew;

      /**
     * 是否推荐，0是不推荐，1是推荐
     */
      private Integer isRecommend;


      private LocalDateTime createdTime;


      private LocalDateTime updateTime;


}
