package com.cyl.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyl.scaffold.constant.GlobalConstant;
import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.exception.BusinessException;
import com.cyl.scaffold.entity.SysMenu;
import com.cyl.scaffold.mapper.SysMenuMapper;
import com.cyl.scaffold.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cyl
 * @date 2023-06-15 15:38
 * @description 系统菜单接口实现类
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public void addMenu(SysMenu sysMenu) {
        // 校验菜单编码
        checkUniqueMenuCode(sysMenu.getMenuCode());

        save(sysMenu);
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        SysMenu menu = getById(sysMenu.getId());
        if (Objects.isNull(menu)) {
            throw new BusinessException(ResultCodeEnum.SYS_MENU_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysMenu.getMenuCode()) && !menu.getMenuCode().equals(sysMenu.getMenuCode())) {
            checkUniqueMenuCode(sysMenu.getMenuCode());
        }

        updateById(sysMenu);
    }

    @Override
    public SysMenu getMenuByMenuCode(String menuCode) {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysMenu::getMenuCode, menuCode);

        return getOne(queryWrapper);
    }

    private void checkUniqueMenuCode(String menuCode) {
        SysMenu sysMenu = getMenuByMenuCode(menuCode);
        if (Objects.nonNull(sysMenu)) {
            throw new BusinessException(ResultCodeEnum.SYS_MENU_MENUCODE_EXISTS);
        }
    }

    @Override
    public List<SysMenu> menuTree() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(SysMenu::getSort);
        List<SysMenu> list = list(queryWrapper);
        return getMenuTree(list);
    }

    @Override
    public List<SysMenu> getPermissionMenus(List<SysMenu> menuList) {
        List<SysMenu> permissionMenus = menuList.stream()
                .filter(item -> item.getType().equals(GlobalConstant.MENU))
                .collect(Collectors.toList());

        permissionMenus = getMenuTree(permissionMenus);

//        return permissionMenus;
        return permissionMenus.get(0).getChildren();
    }

    @Override
    public List<String> getPermissionButtons(List<SysMenu> menuList) {

        List<String> permissionButtons = menuList.stream()
                .filter(item -> item.getType().equals(GlobalConstant.BUTTON))
                .map(SysMenu::getMenuCode)
                .collect(Collectors.toList());

        return permissionButtons;
    }

    private List<SysMenu> getMenuTree(List<SysMenu> list) {
        ArrayList<SysMenu> resultList = new ArrayList<>();
        List<Long> ids = list.stream().map(SysMenu::getId).collect(Collectors.toList());
        for (SysMenu sysMenu : list) {
            if (!ids.contains(sysMenu.getPid())) {
                resultList.add(sysMenu);
            }
        }

        for (SysMenu sysMenu : resultList) {
            List<SysMenu> child = getChildrenMenu(sysMenu.getId(), list);
            sysMenu.setChildren(child);
        }
        return resultList;
    }

    private List<SysMenu> getChildrenMenu(Long menuId, List<SysMenu> list) {
        //子菜单列表
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : list) {
            if (menuId.equals(menu.getPid())) {
                childList.add(menu);
            }
        }

        for (SysMenu menu : childList) {
            List<SysMenu> childrenMenu = getChildrenMenu(menu.getId(), list);
            menu.setChildren(childrenMenu);
        }

        //递归出口 childList长度为0
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}
