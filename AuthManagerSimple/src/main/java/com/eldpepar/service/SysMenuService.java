package com.eldpepar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eldpepar.model.system.SysMenu;
import com.eldpepar.model.vo.AssginMenuVo;
import com.eldpepar.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author eldpepar
 * @since 2023-03-07
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(String id);

    void doAssign(AssginMenuVo assginMenuVo);

    List<SysMenu> findMenuByRoleId(String roleId);

    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);
}
