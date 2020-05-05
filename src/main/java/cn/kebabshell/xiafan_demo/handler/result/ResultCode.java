package cn.kebabshell.xiafan_demo.handler.result;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 08:00
 */
public enum ResultCode {
    SUCCESS(20000, "成功"),
    NO_AUTHORITY(20005, "无权限"),
    NO_LOGIN(20002, "用户未登录"),
    TOKEN_EXPIRED(20003, "登录身份已过期"),
    PWD_ERROR(20004, "密码错误"),
    SYS_ERROR(20006, "系统错误"),
    ERROR(20007, "错误"),
    NO_USER(20001, "用户不存在");

    // 结果状态码
    private Integer code;

    // 结果消息
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
