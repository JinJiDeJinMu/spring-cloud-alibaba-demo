package com.hjb.mapper;

import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.po.SeckillActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-12-29
 */
@Mapper
public interface SeckillActivityMapper extends BaseMapper<SeckillActivity> {

    @Select(value = "<script> SELECT\n" +
            "        ac.id AS activityId,\n" +
            "        ac.title,\n" +
            "        ac.start_time AS startTime,\n" +
            "        ac.end_time AS endTime,\n" +
            "        gd.sku_id AS skuId,\n" +
            "        gd.skill_price AS skillPrice,\n" +
            "        gd.skill_limit_count AS skillLimitCount\n" +
            "        FROM\n" +
            "        hu_seckill_activity ac\n" +
            "        LEFT JOIN hu_seckill_goods gd ON ac.id = gd.activity_id\n" +
            "        WHERE\n" +
            "        ac.STATUS = 0\n" +
            "        AND now( ) &lt;= ac.end_time </script>")
    List<SeckillActivityDTO> sillList();
}
