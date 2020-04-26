package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.PicCommentLike;
import cn.kebabshell.xiafan_demo.common.pojo.PicCommentLikeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PicCommentLikeMapper {
    long countByExample(PicCommentLikeExample example);

    int deleteByExample(PicCommentLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PicCommentLike record);

    int insertSelective(PicCommentLike record);

    List<PicCommentLike> selectByExample(PicCommentLikeExample example);

    PicCommentLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PicCommentLike record, @Param("example") PicCommentLikeExample example);

    int updateByExample(@Param("record") PicCommentLike record, @Param("example") PicCommentLikeExample example);

    int updateByPrimaryKeySelective(PicCommentLike record);

    int updateByPrimaryKey(PicCommentLike record);
}