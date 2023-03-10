package com.eldpepar.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldpepar.common.result.Result;
import com.eldpepar.common.util.helper.MD5;
import com.eldpepar.log.annotation.Log;
import com.eldpepar.log.enums.BusinessType;
import com.eldpepar.model.system.SysUser;
import com.eldpepar.model.vo.SysUserQueryVo;
import com.eldpepar.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author eldpepar
 * @since 2023-03-07
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @Log(title = "用户管理",businessType = BusinessType.EXPORT)
    @ApiOperation("用户列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(page, limit);

        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, sysUserQueryVo);

        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @Log(title = "用户管理",businessType = BusinessType.IMPORT)
    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user) {
        // 输入的用户进行MD5加密
        String encrypt = MD5.encrypt(user.getPassword());
        user.setPassword(encrypt);

        boolean is_Success = sysUserService.save(user);
        if (is_Success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @Log(title = "用户管理",businessType = BusinessType.EXPORT)
    @ApiOperation("根据id查询")
    @GetMapping("/getUser/{id}")
    public Result getUser(@PathVariable String id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @Log(title = "用户管理",businessType = BusinessType.UPDATE)
    @ApiOperation("修改用户")
    @PostMapping("/update")
    public Result update(@RequestBody SysUser user) {
        boolean is_Success = sysUserService.updateById(user);
        if (is_Success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @Log(title = "用户管理",businessType = BusinessType.DELETE)
    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        boolean is_Success = sysUserService.removeById(id);
        if (is_Success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @Log(title = "用户管理",businessType = BusinessType.UPDATE)
    @ApiOperation("更改用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status) {
        boolean is_Success = sysUserService.updateStatus(id, status);
        if (is_Success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}