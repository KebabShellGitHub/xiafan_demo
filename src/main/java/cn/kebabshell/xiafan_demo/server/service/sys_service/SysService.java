package cn.kebabshell.xiafan_demo.server.service.sys_service;


import cn.kebabshell.xiafan_demo.common.custom.RoleAuth;
import cn.kebabshell.xiafan_demo.common.dto.PicBriefDTO;
import cn.kebabshell.xiafan_demo.common.pojo.LogOperation;
import cn.kebabshell.xiafan_demo.common.pojo.Role;
import cn.kebabshell.xiafan_demo.common.pojo.Sort;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/25 下午 09:35
 */
public interface SysService {
    /**
     * 添加图片的分类
     * @param sort
     * @return
     */
    Sort addSort(Sort sort);

    /**
     * 删除分类
     * @param sortId
     * @return
     */
    boolean delSort(Long sortId);

    /**
     * 更新分类
     * @param sort
     * @return
     */
    Sort updateSort(Sort sort);

    /**
     * 查找单个分类信息
     * @param sortId
     * @return
     */
    Sort getSortInfo(Long sortId);

    /**
     * 查找所有分类
     * @return
     */
    List<Sort> getAllSort();

    List<RoleAuth> getAllRoleInfo();

//    Role addRole(Role role);

    Role updateRole(Role role);

    List<LogOperation> getAllOperation(int pageNum, int pageCount);




}
