package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.common.pojo.User;
import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import cn.kebabshell.xiafan_demo.server.service.pic_service.PicService;
import cn.kebabshell.xiafan_demo.server.service.user_service.UserService;
import cn.kebabshell.xiafan_demo.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:39
 */
@RestController
@RequestMapping("/pic")
public class PicController {
    @Autowired
    private PicService service;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public MyResult addPic(@RequestBody PicInfoDTO picInfoDTO){
        return new MyResult(ResultCode.SUCCESS, service.addPic(picInfoDTO));
    }
    @RequiresRoles("general")
    @GetMapping("/del")
    public MyResult delPic(HttpServletRequest request, Long picId){
        String token = request.getHeader("Token");
        if (token == null){
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        if (user == null){
            return new MyResult(ResultCode.NO_USER);
        }else if (!user.getEffective()){
            return new MyResult(ResultCode.ILLEGAL_USER);
        }
        return service.deletePic(user.getId(), picId) ?
                new MyResult(ResultCode.SUCCESS) : new MyResult(ResultCode.ERROR);
    }
    @RequiresRoles("general")
    @PostMapping("/update")
    public MyResult updatePic(HttpServletRequest request, @RequestBody PicInfoDTO picInfoDTO){
        String token = request.getHeader("Token");
        if (token == null){
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        if (user == null){
            return new MyResult(ResultCode.NO_USER);
        }else if (!user.getEffective()){
            return new MyResult(ResultCode.ILLEGAL_USER);
        }
        return new MyResult(ResultCode.SUCCESS, service.updatePic(user.getId(), picInfoDTO));
    }

    @RequiresRoles("root")
    @GetMapping("/info/root")
    public MyResult getPicInfoRoot(Long userId, Long picId){
        PicInfoDTO picInfo = service.getPicInfoByRoot(userId, picId);
        if (picInfo != null){
            return new MyResult(ResultCode.SUCCESS, picInfo);
        }else {
            return new MyResult(ResultCode.NO_PIC);
        }
    }
    @GetMapping("/info")
    public MyResult getPicInfo(Long userId, Long picId){
        PicInfoDTO picInfo = service.getPicInfo(userId, picId);
        if (picInfo != null){
            return new MyResult(ResultCode.SUCCESS, picInfo);
        }else {
            return new MyResult(ResultCode.NO_PIC);
        }
    }
    @RequiresRoles("root")
    @GetMapping("/list/root")
    public MyResult getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount){
        return new MyResult(ResultCode.SUCCESS, service.getPicBriefLimitByRoot(userId, pageNum, pageCount));
    }
    @GetMapping("/list")
    public MyResult getPicBriefLimit(Long userId, int pageNum, int pageCount){
        return new MyResult(ResultCode.SUCCESS, service.getPicBriefLimit(userId, pageNum, pageCount));
    }
}
