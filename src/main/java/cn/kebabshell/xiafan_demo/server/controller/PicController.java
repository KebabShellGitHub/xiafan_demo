package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.handler.result.MyResult;
import cn.kebabshell.xiafan_demo.handler.result.ResultCode;
import cn.kebabshell.xiafan_demo.server.service.pic_service.PicService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:39
 */
@RestController
@RequestMapping("/pic")
public class PicController {
    @Autowired
    private PicService service;

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
