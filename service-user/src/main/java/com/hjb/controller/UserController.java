package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.po.User;
import com.hjb.service.UserService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Usercontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-11-30
 */
@RestController
@Api(tags = "User")
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getUserById(Long id){
        return Result.SUCCESS(userService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询User全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getUserAll(@RequestBody HashMap<String, Object> param){
        List<User> result= userService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询User全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getUserPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<User> result= userService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param user
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateUser(@RequestBody User user){
        return Result.SUCCESS(userService.saveOrUpdate(user));
    }

    /**
    * 批量删除
    * @param ids
    */
    @PreAuthorize(value="hasAuthority('userDelete')")
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteUserById(List<Long> ids){
        return Result.SUCCESS(userService.removeByIds(ids));
    }

}
