package cn.kebabshell.xiafan_demo.handler;

import cn.kebabshell.xiafan_demo.handler.exception.MyTokenExpiredException;
import cn.kebabshell.xiafan_demo.handler.exception.MyUserEffectiveException;
import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by KebabShell
 * on 2020/4/20 下午 11:22
 *
 * 自定义异常处理类
 *
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public MyResult auth(HttpServletRequest request, HttpServletResponse response, AuthorizationException e){
        return new MyResult(ResultCode.NO_AUTHORITY, "无权限");
    }
    @ExceptionHandler(value = MyTokenExpiredException.class)
    @ResponseBody
    public MyResult noToken(HttpServletRequest request, HttpServletResponse response, MyTokenExpiredException e){
        return new MyResult(ResultCode.TOKEN_EXPIRED, e.getMessage());
    }
    @ExceptionHandler(value = MyUserEffectiveException.class)
    @ResponseBody
    public MyResult noToken(HttpServletRequest request, HttpServletResponse response, MyUserEffectiveException e){
        return new MyResult(ResultCode.ILLEGAL_USER, e.getMessage());
    }
}
