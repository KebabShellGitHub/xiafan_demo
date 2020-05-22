package cn.kebabshell.xiafan_demo.handler.result;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by KebabShell
 * on 2020/4/21 下午 07:54
 */
@Getter
@Setter
public class MyResult {
    // 结果状态码
    private Integer code;
    // 结果消息
    private String msg;
    // 结果数据
    private Object data;

    public MyResult() {
        super();
    }

    public MyResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public MyResult(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }
    public MyResult(ResultCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }
    public MyResult(ResultCode code, Object data) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.data = data;
    }
    @Override
    public String toString() {
        return "Result{" +
                "code=" + this.code +
                ", msg='" + this.msg + '\'' +
                ", data=" + this.data +
                '}';
    }
}
