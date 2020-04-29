package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.PicLike;
import cn.kebabshell.xiafan_demo.common.pojo.PicLikeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PicLikeMapper {
    long countByExample(PicLikeExample example);

    int deleteByExample(PicLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PicLike record);

    int insertSelective(PicLike record);

    List<PicLike> selectByExample(PicLikeExample example);

    PicLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PicLike record, @Param("example") PicLikeExample example);

    int updateByExample(@Param("record") PicLike record, @Param("example") PicLikeExample example);

    int updateByPrimaryKeySelective(PicLike record);

    int updateByPrimaryKey(PicLike record);
}