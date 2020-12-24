package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.Category;
import com.hjb.mapper.CategoryMapper;
import com.hjb.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
