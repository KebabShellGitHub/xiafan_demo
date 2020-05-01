package cn.kebabshell.xiafan_demo.server.service.user_service;

import cn.kebabshell.xiafan_demo.common.custom.UserAuth;
import cn.kebabshell.xiafan_demo.common.pojo.User;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:27
 */
public interface UserService {
    /**
     * 根据用户名找user
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 根据用户名找user，带有角色和权限信息
     * @param username
     * @return
     */
    UserAuth findByNameForAuth(String username);

    /**
     * 普通用户注册
     * @param user
     * @return
     */
    User register(User user);
}
