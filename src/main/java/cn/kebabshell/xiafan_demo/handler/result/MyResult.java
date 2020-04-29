package cn.kebabshell.xiafan_demo.handler.result;


/**
 * Created by KebabShell
 * on 2020/4/21 下午 07:54
 */
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
        this.msg = code.getMessage();
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
