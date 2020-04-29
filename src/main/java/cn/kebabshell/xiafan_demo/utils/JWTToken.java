package cn.kebabshell.xiafan_demo.utils;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 08:51
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
