package com.hjb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjb.domain.po.GoodsAttribute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-11-25
 */
public interface GoodsAttributeMapper extends BaseMapper<GoodsAttribute> {

    @Delete("<script>" +
            " delete  from hu_goods_attribute " +
            " where goods_id in" +
            " <foreach collection='goodsId' item='id' open='(' separator=',' close=')'>" +
            " #{id}" +
            " </foreach>" +
            " </script>")
    boolean deleteGoodsAttrByGoodsId(@Param("goodsId") List<Long> goodsIds);
}
