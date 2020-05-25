package cn.kebabshell.xiafan_demo.server.service.sys_service.impl;

import cn.kebabshell.xiafan_demo.common.pojo.Sort;
import cn.kebabshell.xiafan_demo.server.service.sys_service.SysService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/25 下午 09:36
 */
@Service
public class SysServiceImpl implements SysService {
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
    public List<Sort> getAllSort(Long sortId) {
        return null;
    }
}
