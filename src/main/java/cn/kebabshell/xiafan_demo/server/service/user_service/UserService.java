package cn.kebabshell.xiafan_demo.server.service.user_service;

import cn.kebabshell.xiafan_demo.common.custom.UserAuth;
import cn.kebabshell.xiafan_demo.common.pojo.User;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:27
 */
public interface UserService {
    User findByName(String username);

    UserAuth findByNameForAuth(String username);
}
