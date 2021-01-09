package com.hjb.service;

import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.param.ActivityParam;
import com.hjb.domain.po.SeckillActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-29
 */
public interface SeckillActivityService extends IService<SeckillActivity> {

    boolean createdSeckillActivity(ActivityParam activityParam);

    boolean stopActivity(Long id);

    boolean syncActivity(Long activityId,Long secKillSkuId);

    List<SeckillActivityDTO> skillList();

    String kill(Long activityId, Long skuId, Integer num,String path);

    String killUrl(Long skuId);
}
