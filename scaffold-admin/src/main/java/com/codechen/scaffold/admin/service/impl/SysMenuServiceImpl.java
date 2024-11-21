package com.codechen.scaffold.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codechen.scaffold.admin.domain.entity.SysMenu;
import com.codechen.scaffold.admin.domain.request.SysMenuRequest;
import com.codechen.scaffold.admin.domain.vo.SysMenuVo;
import com.codechen.scaffold.admin.mapper.SysMenuMapper;
import com.codechen.scaffold.admin.service.ISysMenuService;
import com.codechen.scaffold.common.constant.CommonConstant;
import com.codechen.scaffold.common.enums.ResultCodeEnum;
import com.codechen.scaffold.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:38
 * @description 系统菜单接口实现类
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public void addMenu(SysMenuRequest sysMenuRequest) {
        // 校验菜单编码
        checkUniqueMenuCode(sysMenuRequest.getMenuCode());

        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuRequest, sysMenu);
        save(sysMenu);
    }

    @Override
    public void updateMenu(Long id, SysMenuRequest sysMenuRequest) {
        SysMenu sysMenu = getById(id);
        if (Objects.isNull(sysMenu)) {
            throw new BusinessException(ResultCodeEnum.SYS_MENU_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysMenuRequest.getMenuCode()) && !sysMenu.getMenuCode().equals(sysMenuRequest.getMenuCode())) {
            checkUniqueMenuCode(sysMenuRequest.getMenuCode());
        }

        BeanUtil.copyProperties(sysMenuRequest, sysMenu);

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
            throw new BusinessException(ResultCodeEnum.SYS_MENU_MENU_CODE_EXISTS);
        }
    }

    @Override
    public List<SysMenuVo> menuTree() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(SysMenu::getSort);
        List<SysMenu> list = list(queryWrapper);
        return getMenuTree(list);
    }

    @Override
    public List<SysMenuVo> getPermissionMenus(List<SysMenu> menuList) {
        List<SysMenu> permissionMenus = menuList.stream()
                .filter(item -> item.getType().equals(CommonConstant.MENU))
                .collect(Collectors.toList());

        List<SysMenuVo> menuTree = getMenuTree(permissionMenus);

        if (CollectionUtils.isNotEmpty(menuTree)) {
//            return menuTree.get(0).getChildren();
            return menuTree;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<String> getPermissionButtons(List<SysMenu> menuList) {

        List<String> permissionButtons = menuList.stream()
                .filter(item -> item.getType().equals(CommonConstant.BUTTON))
                .map(SysMenu::getMenuCode)
                .collect(Collectors.toList());

        return permissionButtons;
    }

    private List<SysMenuVo> getMenuTree(List<SysMenu> list) {
        ArrayList<SysMenuVo> resultList = new ArrayList<>();
        List<Long> ids = list.stream().map(SysMenu::getId).collect(Collectors.toList());
        for (SysMenu sysMenu : list) {
            if (!ids.contains(sysMenu.getPid())) {
                SysMenuVo sysMenuVo = new SysMenuVo();
                BeanUtil.copyProperties(sysMenu, sysMenuVo);
                resultList.add(sysMenuVo);
            }
        }

        for (SysMenuVo sysMenuVo : resultList) {
            List<SysMenu> sysMenuList = getChildrenMenu(sysMenuVo.getId(), list);
            List<SysMenuVo> sysMenuVoList = BeanUtil.copyToList(sysMenuList, SysMenuVo.class);
            sysMenuVo.setChildren(sysMenuVoList);
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
