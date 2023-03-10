package com.eldpepar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eldpepar.common.result.Result;
import com.eldpepar.model.system.SysLoginLog;
import com.eldpepar.model.vo.SysLoginLogQueryVo;
import com.eldpepar.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "日志管理")
@RestController
@RequestMapping(value="/admin/system/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    //条件分页查询登录日志
    @ApiOperation(value = "获取登录日志")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable long page,
                        @PathVariable long limit,
                        SysLoginLogQueryVo sysLoginLogQueryVo) {
        IPage<SysLoginLog> pageModel =  loginLogService.selectPage(page,limit,sysLoginLogQueryVo);
        return Result.ok(pageModel);
    }
}
