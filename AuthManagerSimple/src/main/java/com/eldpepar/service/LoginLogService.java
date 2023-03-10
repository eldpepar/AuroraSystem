package com.eldpepar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eldpepar.model.system.SysLoginLog;
import com.eldpepar.model.vo.SysLoginLogQueryVo;

public interface LoginLogService {

    //登录日志
    public void recordLoginLog(String username,Integer status,
                               String ipaddr,String message);

    //条件分页查询登录日志
    IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo);
}
