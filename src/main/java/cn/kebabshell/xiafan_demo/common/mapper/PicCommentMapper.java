package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.PicComment;
import cn.kebabshell.xiafan_demo.common.pojo.PicCommentExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PicCommentMapper {
    long countByExample(PicCommentExample example);

    int deleteByExample(PicCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PicComment record);

    int insertSelective(PicComment record);

    List<PicComment> selectByExample(PicCommentExample example);

    PicComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PicComment record, @Param("example") PicCommentExample example);

    int updateByExample(@Param("record") PicComment record, @Param("example") PicCommentExample example);

    int updateByPrimaryKeySelective(PicComment record);

    int updateByPrimaryKey(PicComment record);
}