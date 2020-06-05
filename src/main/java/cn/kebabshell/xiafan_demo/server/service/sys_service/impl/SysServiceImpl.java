package cn.kebabshell.xiafan_demo.server.service.sys_service.impl;

import cn.kebabshell.xiafan_demo.common.custom.RoleAuth;
import cn.kebabshell.xiafan_demo.common.mapper.SortMapper;
import cn.kebabshell.xiafan_demo.common.pojo.LogOperation;
import cn.kebabshell.xiafan_demo.common.pojo.Role;
import cn.kebabshell.xiafan_demo.common.pojo.Sort;
import cn.kebabshell.xiafan_demo.common.pojo.SortExample;
import cn.kebabshell.xiafan_demo.server.service.sys_service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/25 下午 09:36
 */
@Service
public class SysServiceImpl implements SysService {
    @Autowired
    private SortMapper sortMapper;

    @Override
    public Sort addSort(Sort sort) {
        return null;
    }

    @Override
    public boolean delSort(Long sortId) {
        return false;
    }

    @Override
    public Sort updateSort(Sort sort) {
        return null;
    }

    @Override
    public Sort getSortInfo(Long sortId) {
        return null;
    }

    @Override
    public List<Sort> getAllSort() {
        List<Sort> sorts = sortMapper.selectByExample(new SortExample());
        return sorts;
    }

    @Override
    public List<RoleAuth> getAllRoleInfo() {
        return null;
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public List<LogOperation> getAllOperation(int pageNum, int pageCount) {
        return null;
    }
}
