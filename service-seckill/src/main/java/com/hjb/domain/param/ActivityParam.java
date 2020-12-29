package com.hjb.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ActivityParam {

    private Long id;

    @NotNull(message = "秒杀活动标题不能为空")
    private String title;

    @NotNull(message = "秒杀活动开始时间不能为空")
    private Date startTime;

    @NotNull(message = "秒杀活动结束时间不能为空")
    private Date endTime;
}
