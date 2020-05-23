package cn.kebabshell.xiafan_demo.server.service.pic_service.impl;

import cn.kebabshell.xiafan_demo.common.dto.PicBriefDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicCommentDTO;
import cn.kebabshell.xiafan_demo.common.dto.PicInfoDTO;
import cn.kebabshell.xiafan_demo.common.mapper.*;
import cn.kebabshell.xiafan_demo.common.pojo.*;
import cn.kebabshell.xiafan_demo.handler.exception.MyAuthException;
import cn.kebabshell.xiafan_demo.server.service.pic_service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by KebabShell
 * on 2020/5/22 下午 07:53
 */
@Service
public class PicServiceImpl implements PicService {
    @Autowired
    private PicMapper picMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PicSortMapper picSortMapper;
    @Autowired
    private SortMapper sortMapper;
    @Autowired
    private PicLikeMapper picLikeMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Autowired
    private PicHitsMapper picHitsMapper;
    @Autowired
    private PicCommentMapper picCommentMapper;

    @Override
    @Transactional
    public PicInfoDTO addPic(PicInfoDTO picInfoDTO) {
        Pic pic = picInfoDTO.getPic();
        pic.setCreateTime(new Date());
        picMapper.insertSelective(pic);
        PicSort picSort = new PicSort();
        picSort.setCreateTime(pic.getCreateTime());
        picSort.setPicId(pic.getId());
        picSort.setSortId(picInfoDTO.getSortId());
        picSortMapper.insertSelective(picSort);

        picInfoDTO.setPic(pic);
        return picInfoDTO;
    }

    @Override
    @Transactional
    public boolean deletePic(Long userId, Long picId) {
        //判断这个图片是不是这个用户的
        Long realUserId = picMapper.selectByPrimaryKey(picId).getUserId();
        if (!userId.equals(realUserId)) {
            throw new MyAuthException("无权限");
        }
        //删除点击记录
        PicHitsExample picHitsExample = new PicHitsExample();
        PicHitsExample.Criteria hitCriteria = picHitsExample.createCriteria();
        hitCriteria.andPicIdEqualTo(picId);
        picHitsMapper.deleteByExample(picHitsExample);
        //删除点赞记录
        PicLikeExample picLikeExample = new PicLikeExample();
        PicLikeExample.Criteria likeCriteria = picLikeExample.createCriteria();
        likeCriteria.andPicIdEqualTo(picId);
        picLikeMapper.deleteByExample(picLikeExample);
        //删除分类记录
        PicSortExample picSortExample = new PicSortExample();
        PicSortExample.Criteria sortCriteria = picSortExample.createCriteria();
        sortCriteria.andPicIdEqualTo(picId);
        picSortMapper.deleteByExample(picSortExample);
        //删除评论记录
        PicCommentExample picCommentExample = new PicCommentExample();
        PicCommentExample.Criteria commentCriteria = picCommentExample.createCriteria();
        commentCriteria.andPicIdEqualTo(picId);
        picCommentMapper.deleteByExample(picCommentExample);
        //删除图片
        picMapper.deleteByPrimaryKey(picId);
        return true;
    }

    @Override
    @Transactional
    public PicInfoDTO updatePic(Long userId, PicInfoDTO picInfoDTO) {
        Long picId = picInfoDTO.getPic().getId();
        //判断这个图片是不是这个用户的
        Long realUserId = picMapper.selectByPrimaryKey(picId).getUserId();
        if (!userId.equals(realUserId)) {
            throw new MyAuthException("无权限");
        }
        //分类
        Long newSortId = picInfoDTO.getSortId();
        if (newSortId != null){
            PicSortExample picSortExample = new PicSortExample();
            PicSortExample.Criteria sortCriteria = picSortExample.createCriteria();
            sortCriteria.andPicIdEqualTo(picId);
            PicSort realPicSort = picSortMapper.selectByExample(picSortExample).get(0);
            //如果数据库的分类信息和新分类信息不一样
            if (!realPicSort.getSortId().equals(newSortId)) {
                //更新分类信息
                PicSort newPicSort = new PicSort();
                newPicSort.setSortId(newSortId);
                newPicSort.setId(realPicSort.getId());
                picSortMapper.updateByPrimaryKeySelective(newPicSort);

                //填充新分类信息
                String sortName = sortMapper.selectByPrimaryKey(newSortId).getName();
                picInfoDTO.setSortName(sortName);
            }
        }
        //图片
        picMapper.updateByPrimaryKeySelective(picInfoDTO.getPic());
        //填充新图片信息
        picInfoDTO.setPic(picMapper.selectByPrimaryKey(picId));

        return picInfoDTO;
    }

    @Override
    public PicInfoDTO getPicInfoByRoot(Long userId, Long picId) {
        //图片实体
        Pic pic = picMapper.selectByPrimaryKey(picId);

        if (pic == null) {
            return null;
        }

        //发布人用户名
        String userName = userMapper.selectByPrimaryKey(pic.getUserId()).getName();
        //拿到分类id
        PicSortExample picSortExample = new PicSortExample();
        PicSortExample.Criteria criteria = picSortExample.createCriteria();
        criteria.andPicIdEqualTo(picId);
        Long sortId = picSortMapper.selectByExample(picSortExample).get(0).getSortId();
        //拿到分类名
        String sortName = sortMapper.selectByPrimaryKey(sortId).getName();
        //点赞数
        PicLikeExample picLikeExample = new PicLikeExample();
        PicLikeExample.Criteria criteria1 = picLikeExample.createCriteria();
        criteria1.andPicIdEqualTo(picId);
        long likeCount = picLikeMapper.countByExample(picLikeExample);
        //我是否有关注ta
        boolean isFollow = false;
        if (userId != null) {
            UserFollowExample userFollowExample = new UserFollowExample();
            UserFollowExample.Criteria criteria2 = userFollowExample.createCriteria();
            criteria2.andUserIdEqualTo(userId)
                    .andFollowedUserIdEqualTo(pic.getUserId());
            if (userFollowMapper.selectByExample(userFollowExample).size() != 0) {
                isFollow = true;
            }
        }
        //点击量
        PicHitsExample picHitsExample = new PicHitsExample();
        PicHitsExample.Criteria criteria3 = picHitsExample.createCriteria();
        criteria3.andPicIdEqualTo(picId);
        long hitCount = picHitsMapper.countByExample(picHitsExample);
        //评论数
        PicCommentExample picCommentExample = new PicCommentExample();
        PicCommentExample.Criteria criteria4 = picCommentExample.createCriteria();
        criteria4.andPicIdEqualTo(picId);
        long commentCount = picCommentMapper.countByExample(picCommentExample);
        return new PicInfoDTO(pic, userName, sortId, sortName, likeCount, isFollow, hitCount, commentCount);
    }

    @Override
    public PicInfoDTO getPicInfo(Long userId, Long picId) {
        PicInfoDTO picInfo = getPicInfoByRoot(userId, picId);
        Boolean effective = picInfo.getPic().getEffective();
        //如果无效就返回null, controller记得判断
        return effective ? picInfo : null;
    }

    @Override
    public List<PicBriefDTO> getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount) {
        PicExample picExample = new PicExample();
        //设置分页
        picExample.setStart(pageNum - 1);
        picExample.setCount(pageCount);

        List<Pic> pics = picMapper.selectByExample(picExample);

        List<PicBriefDTO> picBriefDTOS = new LinkedList<>();
        for (Pic pic : pics) {
            PicInfoDTO picInfo = getPicInfoByRoot(userId, pic.getId());

            picBriefDTOS.add(new PicBriefDTO(pic.getId(), pic.getUserId(),
                    picInfo.getUserName(), picInfo.isFollow(), pic.getName(),
                    pic.getPath(), picInfo.getHitCount(), picInfo.getSortId(),
                    picInfo.getSortName(), picInfo.getCommentCount(),
                    picInfo.getLikeCount()));
        }
        return picBriefDTOS;
    }

    @Override
    public List<PicBriefDTO> getPicBriefLimit(Long userId, int pageNum, int pageCount) {
        PicExample picExample = new PicExample();
        //设置分页
        picExample.setStart(pageNum - 1);
        picExample.setCount(pageCount);
        PicExample.Criteria picExampleCriteria = picExample.createCriteria();
        //合法
        picExampleCriteria.andEffectiveEqualTo(true);
        List<Pic> pics = picMapper.selectByExample(picExample);

        List<PicBriefDTO> picBriefDTOS = new LinkedList<>();
        for (Pic pic : pics) {
            PicInfoDTO picInfo = getPicInfoByRoot(userId, pic.getId());

            picBriefDTOS.add(new PicBriefDTO(pic.getId(), pic.getUserId(),
                    picInfo.getUserName(), picInfo.isFollow(), pic.getName(),
                    pic.getPath(), picInfo.getHitCount(), picInfo.getSortId(),
                    picInfo.getSortName(), picInfo.getCommentCount(),
                    picInfo.getLikeCount()));
        }
        return picBriefDTOS;
    }

    @Override
    public List<PicCommentDTO> getPicCommentLimit(int pageNum, int pageCount) {
        return null;
    }

    @Override
    public List<PicCommentDTO> getPicCommentLimitByRoot(int pageNum, int pageCount) {
        return null;
    }

    @Override
    public List<PicHits> getPicHits(int pageNum, int pageCount) {
        return null;
    }

    @Override
    public List<PicHits> getPicHitsByRoot(int pageNum, int pageCount) {
        return null;
    }
}
