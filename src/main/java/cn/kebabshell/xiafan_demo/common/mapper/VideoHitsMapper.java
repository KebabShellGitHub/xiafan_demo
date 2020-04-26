package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.VideoHits;
import cn.kebabshell.xiafan_demo.common.pojo.VideoHitsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoHitsMapper {
    long countByExample(VideoHitsExample example);

    int deleteByExample(VideoHitsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoHits record);

    int insertSelective(VideoHits record);

    List<VideoHits> selectByExample(VideoHitsExample example);

    VideoHits selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoHits record, @Param("example") VideoHitsExample example);

    int updateByExample(@Param("record") VideoHits record, @Param("example") VideoHitsExample example);

    int updateByPrimaryKeySelective(VideoHits record);

    int updateByPrimaryKey(VideoHits record);
}