package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.PicSort;
import cn.kebabshell.xiafan_demo.common.pojo.PicSortExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PicSortMapper {
    long countByExample(PicSortExample example);

    int deleteByExample(PicSortExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PicSort record);

    int insertSelective(PicSort record);

    List<PicSort> selectByExample(PicSortExample example);

    PicSort selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PicSort record, @Param("example") PicSortExample example);

    int updateByExample(@Param("record") PicSort record, @Param("example") PicSortExample example);

    int updateByPrimaryKeySelective(PicSort record);

    int updateByPrimaryKey(PicSort record);
}