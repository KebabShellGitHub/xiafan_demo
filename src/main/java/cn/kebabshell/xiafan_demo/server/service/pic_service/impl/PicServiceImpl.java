package cn.kebabshell.xiafan_demo.server.service.pic_service.impl;

import cn.kebabshell.xiafan_demo.common.dto.PicAddDTO;
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
    @Autowired
    private PicCommentLikeMapper picCommentLikeMapper;

    @Override
    public Pic getPic(Long picId) {
        return picMapper.selectByPrimaryKey(picId);
    }

    @Override
    @Transactional
    public PicAddDTO addPic(PicAddDTO picAddDTO) {
        Pic pic = new Pic();
        pic.setName(picAddDTO.getName());
        pic.setPath(picAddDTO.getPath());
        pic.setInfo(picAddDTO.getInfo());
        pic.setUserId(picAddDTO.getUserId());
        pic.setCreateTime(new Date());
        picMapper.insertSelective(pic);
        PicSort picSort = new PicSort();
        picSort.setCreateTime(pic.getCreateTime());
        picSort.setPicId(pic.getId());
        picSort.setSortId(picAddDTO.getSortId());
        picSortMapper.insertSelective(picSort);

        picAddDTO.setId(pic.getId());
        return picAddDTO;
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
        User user = userMapper.selectByPrimaryKey(pic.getUserId());
        String userName = user.getName();
        String avatarPath = user.getAvatar();
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
        //有没有点赞
        boolean isLike = false;
        if (userId != null && userId != 0){
            PicLikeExample picLikeExample1 = new PicLikeExample();
            PicLikeExample.Criteria criteria5 = picLikeExample1.createCriteria();
            criteria5.andUserIdEqualTo(userId)
                    .andPicIdEqualTo(picId);
            int size = picLikeMapper.selectByExample(picLikeExample1).size();
            if (size > 0){
                isLike = true;
            }
        }
        //我是否有关注ta
        boolean isFollow = false;
        if (userId != null && userId != 0) {
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
        return new PicInfoDTO(pic, userName,
                sortId, sortName, likeCount,
                isFollow, hitCount, commentCount,
                avatarPath, isLike);
    }

    @Override
    public PicInfoDTO getPicInfo(Long userId, Long picId) {
        PicInfoDTO picInfo = getPicInfoByRoot(userId, picId);
        Boolean effective = picInfo.getPic().getEffective();
        //如果无效就返回null, controller记得判断
        return effective ? picInfo : null;
    }

    @Override
    public List<PicBriefDTO> getPicBriefLimitByAuthorUserIdByRoot(Long authorId, Long userId, int pageNum, int pageCount) {
        return null;
    }

    @Override
    public List<PicBriefDTO> getPicBriefLimitByAuthorUserId(Long authorId, Long userId, int pageNum, int pageCount) {
        PicExample picExample = new PicExample();
        picExample.setOrderByClause("id desc");
        picExample.setStart((pageNum - 1) * pageCount);
        picExample.setCount(pageCount);
        PicExample.Criteria criteria = picExample.createCriteria();
        criteria.andUserIdEqualTo(authorId)
            .andEffectiveEqualTo(true);
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
    public List<PicBriefDTO> getPicBriefLimitByRoot(Long userId, int pageNum, int pageCount) {
        PicExample picExample = new PicExample();

        picExample.setOrderByClause("id desc");
        //设置分页
        picExample.setStart((pageNum - 1) * pageCount);
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

        picExample.setOrderByClause("id desc");
        //设置分页
        if (pageCount != 0){
            picExample.setStart((pageNum - 1) * pageCount);
            picExample.setCount(pageCount);
        }
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
    public List<PicCommentDTO> getPicCommentLimit(Long picId, int pageNum, int pageCount) {
        List<PicCommentDTO> picCommentLimitByRoot = getPicCommentLimitByRoot(picId, pageNum, pageCount);
        for (PicCommentDTO picCommentDTO : picCommentLimitByRoot) {
            //合法
            if (!picCommentDTO.getPicComment().getEffective()){
                picCommentLimitByRoot.remove(picCommentDTO);
            }
        }
        return picCommentLimitByRoot;
    }

    @Override
    public List<PicCommentDTO> getPicCommentLimitByRoot(Long picId, int pageNum, int pageCount) {
        PicCommentExample picCommentExample = new PicCommentExample();
        //分页
        picCommentExample.setStart((pageNum - 1) * pageCount);
        picCommentExample.setCount(pageCount);

        picCommentExample.setOrderByClause("id desc");

        PicCommentExample.Criteria criteria = picCommentExample.createCriteria();
        criteria.andPicIdEqualTo(picId);
        List<PicComment> picComments = picCommentMapper.selectByExample(picCommentExample);

        List<PicCommentDTO> picCommentDTOS = new LinkedList<>();
        for (PicComment picComment : picComments) {

            PicCommentLikeExample picCommentLikeExample = new PicCommentLikeExample();
            PicCommentLikeExample.Criteria criteria1 = picCommentLikeExample.createCriteria();
            criteria1.andCommentIdEqualTo(picComment.getId());
            long likeCount = picCommentLikeMapper.countByExample(picCommentLikeExample);
            User user = userMapper.selectByPrimaryKey(picComment.getUserId());
            //用户名
            String userName = user.getName();
            //头像
            String avatarPath = user.getAvatar();
            picCommentDTOS.add(new PicCommentDTO(picComment, likeCount, userName, avatarPath));
        }
        return picCommentDTOS;
    }

    @Override
    public List<PicHits> getPicHitsByRoot(Long picId, int pageNum, int pageCount) {
        PicHitsExample picHitsExample = new PicHitsExample();
        picHitsExample.setOrderByClause("id desc");
        //分页
        picHitsExample.setStart((pageNum - 1) * pageCount);
        picHitsExample.setCount(pageCount);

        PicHitsExample.Criteria criteria = picHitsExample.createCriteria();
        criteria.andPicIdEqualTo(picId);
        return picHitsMapper.selectByExample(picHitsExample);
    }

    @Override
    public void addHit(PicHits picHits) {
        picHitsMapper.insert(picHits);
    }

    @Override
    public PicCommentDTO addPicComment(PicComment picComment) {
        picCommentMapper.insertSelective(picComment);
        User user = userMapper.selectByPrimaryKey(picComment.getUserId());
        String userName = user.getName();
        String avatarPath = user.getAvatar();
        PicCommentDTO picCommentDTO = new PicCommentDTO();
        picCommentDTO.setPicComment(picComment);
        picCommentDTO.setAvatarPath(avatarPath);
        picCommentDTO.setUserName(userName);

        return picCommentDTO;
    }

    @Override
    @Transactional
    public boolean delPicComment(Long commentId) {
        PicCommentLikeExample picCommentLikeExample = new PicCommentLikeExample();
        PicCommentLikeExample.Criteria criteria = picCommentLikeExample.createCriteria();
        criteria.andCommentIdEqualTo(commentId);
        picCommentLikeMapper.deleteByExample(picCommentLikeExample);
        picCommentMapper.deleteByPrimaryKey(commentId);
        return true;
    }

    @Override
    public List<PicBriefDTO> getPicInSort(Long userId, Long sortId, int pageNum, int pageCount) {
        PicSortExample picSortExample = new PicSortExample();
        picSortExample.setOrderByClause("id desc");
        //分页
        picSortExample.setStart((pageNum - 1) * pageCount);
        picSortExample.setCount(pageCount);
        PicSortExample.Criteria criteria = picSortExample.createCriteria();
        criteria.andSortIdEqualTo(sortId);
        List<PicSort> picSorts = picSortMapper.selectByExample(picSortExample);

        List<PicBriefDTO> picBriefDTOS = new LinkedList<>();
        for (PicSort picSort : picSorts) {
            Long picId = picSort.getPicId();
            PicBriefDTO picBriefByPicId = getPicBriefByPicId(userId, picId);
            picBriefDTOS.add(picBriefByPicId);
        }
        return picBriefDTOS;
    }

    @Override
    public boolean addLike(PicLike picLike) {
        picLike.setCreateTime(new Date());
        return picLikeMapper.insertSelective(picLike) > 0;
    }

    @Override
    public boolean delLike(PicLike picLike) {
        PicLikeExample picLikeExample = new PicLikeExample();
        PicLikeExample.Criteria criteria = picLikeExample.createCriteria();
        criteria.andPicIdEqualTo(picLike.getPicId())
                .andUserIdEqualTo(picLike.getUserId());
        int i = picLikeMapper.deleteByExample(picLikeExample);
        return i > 0;
    }

    private PicBriefDTO getPicBriefByPicId(Long userId, Long picId){
        PicInfoDTO picInfo = getPicInfo(userId, picId);
        Pic pic = picInfo.getPic();
        return new PicBriefDTO(pic.getId(), pic.getUserId(),
                picInfo.getUserName(), picInfo.isFollow(), pic.getName(),
                pic.getPath(), picInfo.getHitCount(), picInfo.getSortId(),
                picInfo.getSortName(), picInfo.getCommentCount(),
                picInfo.getLikeCount());
    }
    @Override
    public List<PicHits> getPicHits(Long picId, int pageNum, int pageCount) {
        List<PicHits> picHitsByRoot = getPicHitsByRoot(picId, pageNum, pageCount);
        //脱敏
        for (PicHits picHits : picHitsByRoot) {
            picHits.setIp("");
        }
        return picHitsByRoot;
    }
}
