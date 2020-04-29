package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.common.pojo.User;
import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import cn.kebabshell.xiafan_demo.server.service.user_service.UserService;
import cn.kebabshell.xiafan_demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:38
 */
@RestController("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public MyResult login(User user){
        User userGot = service.findByName(user.getName());
        if (userGot == null){
            return new MyResult(ResultCode.NO_USER);
        }else if (!user.getPassword().equals(userGot.getPassword())){
            return new MyResult(ResultCode.PWD_ERROR);
        }else {
            String token = JWTUtil.createToken(user.getName());
            return new MyResult(ResultCode.SUCCESS.getCode(), token, userGot);
        }
    }

}
