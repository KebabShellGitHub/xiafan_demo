package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.RoleAuth;
import cn.kebabshell.xiafan_demo.common.pojo.RoleAuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleAuthMapper {
    long countByExample(RoleAuthExample example);

    int deleteByExample(RoleAuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleAuth record);

    int insertSelective(RoleAuth record);

    List<RoleAuth> selectByExample(RoleAuthExample example);

    RoleAuth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleAuth record, @Param("example") RoleAuthExample example);

    int updateByExample(@Param("record") RoleAuth record, @Param("example") RoleAuthExample example);

    int updateByPrimaryKeySelective(RoleAuth record);

    int updateByPrimaryKey(RoleAuth record);
}