package cn.kebabshell.xiafan_demo.server.service.pic_service;

import cn.kebabshell.xiafan_demo.common.dto.PicBriefDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicCommentDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.common.pojo.Pic;
import cn.kebabshell.xiafan_demo.common.pojo.PicHits;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/22 下午 04:05
 */
public interface PicService {
    /**
     *
     * @param picInfoDTO
     * @return
     */
    PicInfoDTO addPic(PicInfoDTO picInfoDTO);

    /**
     *
     * @param picId
     * @return
     */
    boolean deletePic(Long userId, Long picId);

    /**
     *
     * @param picInfoDTO
     * @return
     */
    PicInfoDTO updatePic(Long userId, PicInfoDTO picInfoDTO);

    /**
     * 拿到图片详情信息，包括失效资源，需要管理员权限
     * @param userId
     * @param picId
     * @return
     */
    PicInfoDTO getPicInfoByRoot(Long userId, Long picId);

    /**
     * 拿到图片详情信息，不包括失效资源
     * @param userId
     * @param picId
     * @return
     */
    PicInfoDTO getPicInfo(Long userId, Long picId);

    /**
     * 拿到所有图片简介，包括失效资源，需要管理员权限
     * @param userId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount);

    /**
     * 拿到所有图片简介信息，不包括失效资源
     * @param userId
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicBriefDTO> getPicBriefLimit(Long userId, int pageNum, int pageCount);

    /**
     * 拿到图片所有评论，不包括失效资源
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicCommentDTO> getPicCommentLimit(int pageNum, int pageCount);

    /**
     * 拿到图片所有评论，包括失效资源，需要管理员权限
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicCommentDTO> getPicCommentLimitByRoot(int pageNum, int pageCount);

    /**
     * 获取图片点击量的具体情况，不包括敏感信息
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicHits> getPicHits(int pageNum, int pageCount);

    /**
     * 获取图片点击量的具体情况，包括敏感信息（ip、地理位置）
     * @param pageNum
     * @param pageCount
     * @return
     */
    List<PicHits> getPicHitsByRoot(int pageNum, int pageCount);



}
