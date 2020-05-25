package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:40
 */
@RestController
public class SysController {
    @GetMapping("/error")
    public MyResult error(String msg){
        return new MyResult(ResultCode.ERROR, msg);
    }
}
