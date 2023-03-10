package com.eldpepar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldpepar.common.result.Result;
import com.eldpepar.log.annotation.Log;
import com.eldpepar.log.enums.BusinessType;
import com.eldpepar.model.system.SysRole;
import com.eldpepar.model.vo.AssginRoleVo;
import com.eldpepar.model.vo.SysRoleQueryVo;
import com.eldpepar.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //查询所有记录
    @ApiOperation("查询所有接口")
    @Log(title = "角色管理",businessType = BusinessType.EXPORT)
    @GetMapping("/findAll")
    public Result findAllRole() {
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    //根据ID查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @Log(title = "角色管理",businessType = BusinessType.EXPORT)
    @ApiOperation("根据ID查询")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }

    //删除记录
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @Log(title = "角色管理",businessType = BusinessType.DELETE)
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/remove/{id}")
    public Result removeRole(@PathVariable Long id) {
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //分页查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @Log(title = "角色管理",businessType = BusinessType.EXPORT)
    @ApiOperation("分页查询接口")
    @GetMapping("/{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo) {
        //创建page对象
        Page<SysRole> pageParam = new Page<>(page, limit);

        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, sysRoleQueryVo);

        return Result.ok(pageModel);
    }

    //添加接口
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @Log(title = "角色管理",businessType = BusinessType.IMPORT)
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result saveRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //修改（最终修改）
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @Log(title = "角色管理",businessType = BusinessType.UPDATE)
    @ApiOperation("最终修改")
    @PostMapping("/update")
    public Result updateRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //批量删除接口
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @Log(title = "角色管理",businessType = BusinessType.DELETE)
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return Result.ok();
    }

    // 用户角色管理
    @ApiOperation("获取用户的角色数据")
    @GetMapping("/toAssign/{userId}")
    @Log(title = "角色管理",businessType = BusinessType.EXPORT)
    public Result toAssign(@PathVariable String userId) {
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation("用户分配角色")
    @PostMapping("/doAssign")
    @Log(title = "角色管理",businessType = BusinessType.IMPORT)
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
}