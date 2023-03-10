package com.eldpepar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eldpepar.model.system.SysUser;
import com.eldpepar.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author eldpepar
 * @since 2023-03-07
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    boolean updateStatus(String id, Integer status);

    // 根据username查询数据
    SysUser getUserInfoByUserName(String username);

    // 获取用户信息
    Map<String, Object> getUserInfo(String username);
}
