package cn.kebabshell.xiafan_demo.common.custom;

import cn.kebabshell.xiafan_demo.common.pojo.Role;
import cn.kebabshell.xiafan_demo.common.pojo.User;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/4/29 下午 01:59
 */
public class UserAuth {
    private User user;
    private List<RoleAuth> roleAuthList;

    public UserAuth(User user, List<RoleAuth> roleAuthList) {
        this.user = user;
        this.roleAuthList = roleAuthList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RoleAuth> getRoleAuthList() {
        return roleAuthList;
    }

    public void setRoleAuthList(List<RoleAuth> roleAuthList) {
        this.roleAuthList = roleAuthList;
    }
}
