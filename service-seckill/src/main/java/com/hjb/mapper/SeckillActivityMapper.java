package com.hjb.mapper;

import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.po.SeckillActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-12-29
 */
public interface SeckillActivityMapper extends BaseMapper<SeckillActivity> {

    List<SeckillActivityDTO> sillList();
}
