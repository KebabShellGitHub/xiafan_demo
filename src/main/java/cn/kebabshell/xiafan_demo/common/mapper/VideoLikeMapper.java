package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.VideoLike;
import cn.kebabshell.xiafan_demo.common.pojo.VideoLikeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VideoLikeMapper {
    long countByExample(VideoLikeExample example);

    int deleteByExample(VideoLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoLike record);

    int insertSelective(VideoLike record);

    List<VideoLike> selectByExample(VideoLikeExample example);

    VideoLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoLike record, @Param("example") VideoLikeExample example);

    int updateByExample(@Param("record") VideoLike record, @Param("example") VideoLikeExample example);

    int updateByPrimaryKeySelective(VideoLike record);

    int updateByPrimaryKey(VideoLike record);
}