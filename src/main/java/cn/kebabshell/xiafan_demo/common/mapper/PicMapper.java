package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.Pic;
import cn.kebabshell.xiafan_demo.common.pojo.PicExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PicMapper {
    long countByExample(PicExample example);

    int deleteByExample(PicExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Pic record);

    int insertSelective(Pic record);

    List<Pic> selectByExample(PicExample example);

    Pic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Pic record, @Param("example") PicExample example);

    int updateByExample(@Param("record") Pic record, @Param("example") PicExample example);

    int updateByPrimaryKeySelective(Pic record);

    int updateByPrimaryKey(Pic record);
}