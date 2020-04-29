package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.PicHits;
import cn.kebabshell.xiafan_demo.common.pojo.PicHitsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PicHitsMapper {
    long countByExample(PicHitsExample example);

    int deleteByExample(PicHitsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PicHits record);

    int insertSelective(PicHits record);

    List<PicHits> selectByExample(PicHitsExample example);

    PicHits selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PicHits record, @Param("example") PicHitsExample example);

    int updateByExample(@Param("record") PicHits record, @Param("example") PicHitsExample example);

    int updateByPrimaryKeySelective(PicHits record);

    int updateByPrimaryKey(PicHits record);
}