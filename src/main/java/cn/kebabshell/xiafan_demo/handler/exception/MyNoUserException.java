package cn.kebabshell.xiafan_demo.handler.exception;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 09:02
 */
public class MyNoUserException extends RuntimeException {
    public MyNoUserException(String msg){
        super(msg);
    }
}
