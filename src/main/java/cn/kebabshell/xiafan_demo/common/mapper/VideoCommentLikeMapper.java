package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.VideoCommentLike;
import cn.kebabshell.xiafan_demo.common.pojo.VideoCommentLikeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VideoCommentLikeMapper {
    long countByExample(VideoCommentLikeExample example);

    int deleteByExample(VideoCommentLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoCommentLike record);

    int insertSelective(VideoCommentLike record);

    List<VideoCommentLike> selectByExample(VideoCommentLikeExample example);

    VideoCommentLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoCommentLike record, @Param("example") VideoCommentLikeExample example);

    int updateByExample(@Param("record") VideoCommentLike record, @Param("example") VideoCommentLikeExample example);

    int updateByPrimaryKeySelective(VideoCommentLike record);

    int updateByPrimaryKey(VideoCommentLike record);
}