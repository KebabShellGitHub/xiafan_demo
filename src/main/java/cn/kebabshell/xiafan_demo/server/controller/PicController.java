package cn.kebabshell.xiafan_demo.server.controller;

import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.common.pojo.PicComment;
import cn.kebabshell.xiafan_demo.common.pojo.PicHits;
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
import java.util.Date;

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
    public MyResult addPic(@RequestBody PicInfoDTO picInfoDTO) {

        return new MyResult(ResultCode.SUCCESS, service.addPic(picInfoDTO));
    }

    @RequiresRoles("general")
    @GetMapping("/del")
    public MyResult delPic(HttpServletRequest request, Long picId) {
        String token = request.getHeader("Token");
        if (token == null) {
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        if (user == null) {
            return new MyResult(ResultCode.NO_USER);
        } else if (!user.getEffective()) {
            return new MyResult(ResultCode.ILLEGAL_USER);
        }
        return service.deletePic(user.getId(), picId) ?
                new MyResult(ResultCode.SUCCESS) : new MyResult(ResultCode.ERROR);
    }

    @RequiresRoles("general")
    @PostMapping("/update")
    public MyResult updatePic(HttpServletRequest request, @RequestBody PicInfoDTO picInfoDTO) {
        String token = request.getHeader("Token");
        if (token == null) {
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        if (user == null) {
            return new MyResult(ResultCode.NO_USER);
        } else if (!user.getEffective()) {
            return new MyResult(ResultCode.ILLEGAL_USER);
        }
        return new MyResult(ResultCode.SUCCESS, service.updatePic(user.getId(), picInfoDTO));
    }

    @RequiresRoles("root")
    @GetMapping("/info/root")
    public MyResult getPicInfoRoot(Long userId, Long picId) {
        PicInfoDTO picInfo = service.getPicInfoByRoot(userId, picId);
        if (picInfo != null) {
            return new MyResult(ResultCode.SUCCESS, picInfo);
        } else {
            return new MyResult(ResultCode.NO_PIC);
        }
    }

    @GetMapping("/info")
    public MyResult getPicInfo(HttpServletRequest request, Long userId, Long picId) {

        PicInfoDTO picInfo = service.getPicInfo(userId, picId);
        if (picInfo != null) {
            //新增访问记录
            // 访问者的IP
            String ip = request.getRemoteAddr();
            PicHits picHits = new PicHits();
            picHits.setIp(ip);
            //picHits.setLocation("");
            service.addHit(picHits);

            return new MyResult(ResultCode.SUCCESS, picInfo);
        } else {
            return new MyResult(ResultCode.NO_PIC);
        }
    }

    @RequiresRoles("root")
    @GetMapping("/list/root")
    public MyResult getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount) {
        return new MyResult(ResultCode.SUCCESS, service.getPicBriefLimitByRoot(userId, pageNum, pageCount));
    }

    @GetMapping("/list")
    public MyResult getPicBriefLimit(Long userId, int pageNum, int pageCount) {
        return new MyResult(ResultCode.SUCCESS, service.getPicBriefLimit(userId, pageNum, pageCount));
    }

    @GetMapping("/comment")
    public MyResult getCommentLimit(Long picId, int pageNum, int pageCount) {
        return new MyResult(ResultCode.SUCCESS, service.getPicCommentLimit(picId, pageNum, pageCount));
    }

    @RequiresRoles("root")
    @GetMapping("/comment/root")
    public MyResult getCommentLimitByRoot(Long picId, int pageNum, int pageCount) {
        return new MyResult(ResultCode.SUCCESS, service.getPicCommentLimitByRoot(picId, pageNum, pageCount));
    }

    @RequiresRoles("root")
    @GetMapping("/hit/root")
    public MyResult getHitsByRoot(HttpServletRequest request, Long picId, int pageNum, int pageCount) {
        String token = request.getHeader("Token");
        if (token == null || token.isEmpty()) {
            return new MyResult(ResultCode.NO_LOGIN);
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        Long realUserId = service.getPic(picId).getUserId();
        if (realUserId == 0) {
            return new MyResult(ResultCode.NO_PIC);
        } else if (!realUserId.equals(user.getId())) {
            return new MyResult(ResultCode.NO_AUTHORITY);
        }
        return new MyResult(ResultCode.SUCCESS, service.getPicHitsByRoot(picId, pageNum, pageCount));
    }

    @GetMapping("/hit")
    public MyResult getHits(HttpServletRequest request, Long picId, int pageNum, int pageCount) {
        String token = request.getHeader("Token");
        if (token == null || token.isEmpty()) {
            return new MyResult(ResultCode.NO_LOGIN);
        }
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        Long realUserId = service.getPic(picId).getUserId();
        if (realUserId == 0) {
            return new MyResult(ResultCode.NO_PIC);
        } else if (!realUserId.equals(user.getId())) {
            return new MyResult(ResultCode.NO_AUTHORITY);
        }
        return new MyResult(ResultCode.SUCCESS, service.getPicHits(picId, pageNum, pageCount));
    }

    @PostMapping("/comment/new")
    public MyResult addComment(@RequestBody PicComment picComment) {
        picComment.setCreateTime(new Date());
        return new MyResult(ResultCode.SUCCESS, service.addPicComment(picComment));
    }

    @GetMapping("/comment/del")
    public MyResult delComment(HttpServletRequest request, Long commentId) {
        String token = request.getHeader("Token");
        String userName = JWTUtil.getUserName(token);
        User user = userService.findByName(userName);
        if (commentId != 0 && commentId.equals(user.getId())) {
            service.delPicComment(commentId);
        } else {
            return new MyResult(ResultCode.NO_AUTHORITY);
        }
        return new MyResult(ResultCode.SUCCESS);
    }

    @GetMapping("/sort")
    public MyResult getPicsInSort(HttpServletRequest request, Long sortId, int pageNum, int pageCount) {
        String token = request.getHeader("Token");
        Long userId = null;
        if (token != null && !token.isEmpty()) {
            String userName = JWTUtil.getUserName(token);
            userId = userService.findByName(userName).getId();
        }
        return new MyResult(ResultCode.SUCCESS, service.getPicInSort(userId, sortId, pageNum, pageCount));
    }
}
