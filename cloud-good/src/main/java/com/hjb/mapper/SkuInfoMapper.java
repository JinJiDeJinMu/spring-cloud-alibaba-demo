package com.hjb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjb.domain.po.SkuInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    @Delete("<script>" +
            " delete  from hu_sku_info " +
            " where goods_id in" +
            " <foreach collection='goodsId' item='id' open='(' separator=',' close=')'>" +
            " #{id}" +
            " </foreach>" +
            " </script>")
    boolean deleteSKUByGoodsId(@Param("goodsId") List<Long> goodsIds);
}
