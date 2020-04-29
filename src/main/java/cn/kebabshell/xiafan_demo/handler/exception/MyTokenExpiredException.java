package cn.kebabshell.xiafan_demo.handler.exception;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 07:42
 */
public class MyTokenExpiredException extends RuntimeException {
    public MyTokenExpiredException(String msg){
        super(msg);
    }
}
