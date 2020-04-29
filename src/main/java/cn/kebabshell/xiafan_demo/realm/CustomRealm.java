package cn.kebabshell.xiafan_demo.realm;
import cn.kebabshell.xiafan_demo.common.custom.RoleAuth;
import cn.kebabshell.xiafan_demo.common.custom.UserAuth;
import cn.kebabshell.xiafan_demo.common.pojo.Authority;
import cn.kebabshell.xiafan_demo.common.pojo.User;
import cn.kebabshell.xiafan_demo.handler.exception.MyNoUserException;
import cn.kebabshell.xiafan_demo.handler.exception.MyTokenExpiredException;
import cn.kebabshell.xiafan_demo.server.service.user_service.UserService;
import cn.kebabshell.xiafan_demo.utils.JWTToken;
import cn.kebabshell.xiafan_demo.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by KebabShell
 * on 2020/4/20 下午 09:28
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 设置realm的名字
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("CustomRealm");
    }

    /**
     * 授权
     * <p>
     * 先认证，再授权！！
     * 根据安全数据获取用户的权限/角色
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //获取安全数据(username)
        String username = JWTUtil.getUsername(principalCollection.toString());
        //根据username拿到user
        UserAuth userAuth = userService.findByNameForAuth(username);
        //拿到用户的角色/权限
        Set<String> roles = new HashSet<>();
        Set<String> auth = new HashSet<>();
        for (RoleAuth roleAuth : userAuth.getRoleAuthList()) {
            roles.add(roleAuth.getRole().getName());
            for (Authority authority : roleAuth.getAuthorityList()) {
                auth.add(authority.getOperation());
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.setRoles(roles);
        info.setStringPermissions(auth);
        //返回权限/角色信息，交给shiro判断
        return info;
    }

    /**
     * 认证（登录用）
     * subject.login过来的
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("登录");
        //login传进的token
        String token = (String) authenticationToken.getCredentials();
        //解析拿到username，并查找用户且判断token的合法性
        String username = JWTUtil.getUsername(token);
        User user = userService.findByName(username);
        if (user == null) {
            //如果用户不存在,抛异常
            throw new MyNoUserException("找不到此用户");
        } else if (JWTUtil.verify(token)) {
            //如果过期,抛异常
            throw new MyTokenExpiredException("token过期了");
        } else {
            //如果用户存在且token不过期
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
    }
}
