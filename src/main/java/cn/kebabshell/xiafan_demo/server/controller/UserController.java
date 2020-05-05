package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.common.pojo.User;
import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import cn.kebabshell.xiafan_demo.server.service.user_service.UserService;
import cn.kebabshell.xiafan_demo.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:38
 * <p>
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public MyResult login(User user) {
        User userGot = service.findByName(user.getName());
        if (userGot == null) {
            //如果用户不存在
            return new MyResult(ResultCode.NO_USER);
        } else if (!user.getPassword().equals(userGot.getPassword())) {
            //如果密码错误
            return new MyResult(ResultCode.PWD_ERROR);
        } else {
            //存在且密码正确
            //生成token，并返回
            String token = JWTUtil.createToken(user.getName());
            return new MyResult(ResultCode.SUCCESS.getCode(), token, userGot);
        }
    }

    /**
     * 注销
     *
     * @return
     */
    @GetMapping("/logout")
    @RequiresRoles("general")
    public MyResult logout() {
        //让前端删除token
        return new MyResult(ResultCode.SUCCESS);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public MyResult register(User user) {
        //数据校验交给前端
        User newUser = service.register(user);
        return new MyResult(ResultCode.SUCCESS.getCode(), "注册成功", newUser);
    }


    /**
     * 测试
     *
     * @return
     */
    @RequiresRoles("root")
    @RequiresPermissions("all")
    @GetMapping("/test")
    public MyResult test() {
        System.out.println("success");
        return new MyResult(ResultCode.SUCCESS);
    }

    /**
     * 删除用户
     * @param id
     * @param name
     * @return
     */
    @RequiresRoles("root")
    @PostMapping("/delete")
    public MyResult deleteByIdOrName(Long id, String name) {
        if (id != null) {
            if (service.deleteById(id))
                return new MyResult(ResultCode.SUCCESS);
            else
                return new MyResult(ResultCode.SYS_ERROR);
        } else if (name != null){
            if (service.deleteByName(name))
                return new MyResult(ResultCode.SUCCESS);
            else
                return new MyResult(ResultCode.SYS_ERROR);
        } else {
            return new MyResult(ResultCode.SYS_ERROR, "无效参数");
        }
    }
}
