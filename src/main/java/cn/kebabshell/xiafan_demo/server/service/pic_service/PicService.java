package cn.kebabshell.xiafan_demo.server.service.pic_service;

import cn.kebabshell.xiafan_demo.common.dto.PicAddDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicBriefDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicCommentDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.common.pojo.*;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/22 下午 04:05
 */
public interface PicService {
    /**
     * @param picId
     * @return
     */
    Pic getPic(Long picId);

    /**
     * @param picAddDTO
     * @return
     */
    PicAddDTO addPic(PicAddDTO picAddDTO);

    /**
     * @param picId
     * @return
     */
    boolean deletePic(Long userId, Long picId);

    /**
     * @param picInfoDTO
     * @return
     */
    PicInfoDTO updatePic(Long userId, PicInfoDTO picInfoDTO);

    /**
     * 拿到图片详情信息，包括失效资源，需要管理员权限
     *
     * @param userId
     * @param picId
     * @return
     */
    PicInfoDTO getPicInfoByRoot(Long userId, Long picId);

    /**
     * 拿到图片详情信息，不包括失效资源
     *
     * @param userId
     * @param picId
     * @return
     */
    PicInfoDTO getPicInfo(Long userId, Long picId);


    /**
     * 根据用户(作者)id查找他的所有图片(需要root)
     * @param authorId 作者id
     * @param userId 请求的用户(用于看粉丝关系)
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimitByAuthorUserIdByRoot(Long authorId, Long userId, int pageNum, int pageCount);
    /**
     * 根据用户(作者)id查找他的所有图片(排除非法)
     * @param authorId 作者id
     * @param userId 请求的用户(用于看粉丝关系)
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimitByAuthorUserId(Long authorId, Long userId, int pageNum, int pageCount);

    /**
     * 拿到所有图片简介，包括失效资源，需要管理员权限
     *
     * @param userId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount);

    /**
     * 拿到所有图片简介信息，不包括失效资源
     *
     * @param userId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimit(Long userId, int pageNum, int pageCount);

    /**
     * 拿到图片所有评论，不包括失效资源
     *
     * @param picId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicCommentDTO> getPicCommentLimit(Long picId, int pageNum, int pageCount);

    /**
     * 拿到图片所有评论，包括失效资源，需要管理员权限
     *
     * @param picId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicCommentDTO> getPicCommentLimitByRoot(Long picId, int pageNum, int pageCount);

    /**
     * 获取图片点击量的具体情况，不包括敏感信息
     *
     * @param picId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicHits> getPicHits(Long picId, int pageNum, int pageCount);

    /**
     * 获取图片点击量的具体情况，包括敏感信息（ip、地理位置）
     *
     * @param picId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicHits> getPicHitsByRoot(Long picId, int pageNum, int pageCount);

    /**
     * 添加点击量
     *
     * @param picHits
     */
    void addHit(PicHits picHits);

    /**
     * 添加评论
     *
     * @param picComment
     * @return
     */
    PicCommentDTO addPicComment(PicComment picComment);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    boolean delPicComment(Long commentId);

    /**
     * 查找此分类下的所有图片
     * @param sortId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicInSort(Long userId, Long sortId, int pageNum, int pageCount);

    /**
     * 点赞
     * @param picLike
     * @return
     */
    boolean addLike(PicLike picLike);

    /**
     * 取消点赞
     * @param picLike
     * @return
     */
    boolean delLike(PicLike picLike);
}
