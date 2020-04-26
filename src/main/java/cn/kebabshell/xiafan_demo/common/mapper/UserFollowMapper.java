package cn.kebabshell.xiafan_demo.common.mapper;

import cn.kebabshell.xiafan_demo.common.pojo.UserFollow;
import cn.kebabshell.xiafan_demo.common.pojo.UserFollowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowMapper {
    long countByExample(UserFollowExample example);

    int deleteByExample(UserFollowExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFollow record);

    int insertSelective(UserFollow record);

    List<UserFollow> selectByExample(UserFollowExample example);

    UserFollow selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFollow record, @Param("example") UserFollowExample example);

    int updateByExample(@Param("record") UserFollow record, @Param("example") UserFollowExample example);

    int updateByPrimaryKeySelective(UserFollow record);

    int updateByPrimaryKey(UserFollow record);
}