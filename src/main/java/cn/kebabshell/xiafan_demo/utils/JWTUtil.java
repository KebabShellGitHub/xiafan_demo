package cn.kebabshell.xiafan_demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 07:18
 */
public class JWTUtil {
    private static long ttl = 1000 * 60 * 10;//失效时间=十分钟
    private static String key = "test-key";

    /**
     * 生成Token
     * 内容为username，密钥为密码
     * @param username
     * @return
     */
    public static String createToken(String username){
        long now = System.currentTimeMillis();
        //过期时间
        long exp = now + ttl;
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置唯一标识
                .setId(username)
                //设置JWT的主体，即它的所有人
                .setSubject(username)
                //设置加密算法和密钥
                .signWith(SignatureAlgorithm.HS256, key)
                //设置签发时间
                .setIssuedAt(new Date(now))
                //设置过期时间
                .setExpiration(new Date(exp));
        //生成token
        return jwtBuilder.compact();
    }

    /**
     * 看token是否过期
     * @param token
     * @return 过期返回true
     */
    public static boolean verify(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        if (claims.getExpiration().getTime() > System.currentTimeMillis()){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 拿到username
     * @param token
     * @return
     */
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}
