package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.LogOperation;
import cn.kebabshell.xiafan_demo.common.pojo.LogOperationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogOperationMapper {
    long countByExample(LogOperationExample example);

    int deleteByExample(LogOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogOperation record);

    int insertSelective(LogOperation record);

    List<LogOperation> selectByExample(LogOperationExample example);

    LogOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByExample(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByPrimaryKeySelective(LogOperation record);

    int updateByPrimaryKey(LogOperation record);
}