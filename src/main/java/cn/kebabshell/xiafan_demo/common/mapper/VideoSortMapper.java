package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.VideoSort;
import cn.kebabshell.xiafan_demo.common.pojo.VideoSortExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VideoSortMapper {
    long countByExample(VideoSortExample example);

    int deleteByExample(VideoSortExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoSort record);

    int insertSelective(VideoSort record);

    List<VideoSort> selectByExample(VideoSortExample example);

    VideoSort selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoSort record, @Param("example") VideoSortExample example);

    int updateByExample(@Param("record") VideoSort record, @Param("example") VideoSortExample example);

    int updateByPrimaryKeySelective(VideoSort record);

    int updateByPrimaryKey(VideoSort record);
}