package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import cn.kebabshell.xiafan_demo.server.service.sys_service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:40
 */
@RestController
@RequestMapping("/sys")
public class SysController {
    @Autowired
    private SysService service;

    @GetMapping("/error")
    public MyResult error(String msg){
        return new MyResult(ResultCode.ERROR, msg);
    }
    @GetMapping("/sort/all")
    public MyResult getAllSort(){
        return new MyResult(ResultCode.SUCCESS, service.getAllSort());
    }
}
