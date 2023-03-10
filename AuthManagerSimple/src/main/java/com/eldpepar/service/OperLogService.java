package com.eldpepar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eldpepar.model.system.SysOperLog;
import com.eldpepar.model.vo.SysOperLogQueryVo;

public interface OperLogService {

    public void saveSysLog(SysOperLog sysOperLog);

    //操作日志分页查询
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);
}
