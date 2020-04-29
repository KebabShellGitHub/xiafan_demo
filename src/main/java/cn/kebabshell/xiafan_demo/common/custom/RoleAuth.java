package cn.kebabshell.xiafan_demo.common.custom;

import cn.kebabshell.xiafan_demo.common.pojo.Authority;
import cn.kebabshell.xiafan_demo.common.pojo.Role;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/4/29 下午 02:02
 */
public class RoleAuth {
    private Role role;
    private List<Authority> authorityList;

    public RoleAuth(Role role, List<Authority> authorityList) {
        this.role = role;
        this.authorityList = authorityList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }
}
