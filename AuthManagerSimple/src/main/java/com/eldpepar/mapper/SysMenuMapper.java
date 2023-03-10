package com.eldpepar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eldpepar.model.system.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author eldpepar
 * @since 2023-03-07
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListUserId(String userId);
}