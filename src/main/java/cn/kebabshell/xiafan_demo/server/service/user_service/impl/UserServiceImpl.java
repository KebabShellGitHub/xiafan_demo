package cn.kebabshell.xiafan_demo.server.service.user_service.impl;

import cn.kebabshell.xiafan_demo.common.custom.UserAuth;
import cn.kebabshell.xiafan_demo.common.custom.RoleAuth;
import cn.kebabshell.xiafan_demo.common.mapper.*;
import cn.kebabshell.xiafan_demo.common.pojo.*;
import cn.kebabshell.xiafan_demo.handler.exception.MyRegisterException;
import cn.kebabshell.xiafan_demo.server.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by KebabShell
 * on 2020/4/29 下午 01:50
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public User findByName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(username);
        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public UserAuth findByNameForAuth(String username) {
        //待优化
        // 想法：第一次查询，检查redis里面有没有，有就直接返回，没有就查数据库，查完存进redis，过期时间与token一致

        //取到user
        User user = findByName(username);
        //根据userId拿到user-role表的信息，即拿到这个user所拥有的角色id
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        //这是自定义的RoleAuth，要传进UserAuth，并返回
        List<RoleAuth> roleAuths = new ArrayList<>();

        //根据每个角色id获取每个角色拥有的权限
        for (UserRole userRole : userRoles) {
            //拿到角色实体
            Long roleId = userRole.getRoleId();
            Role role = roleMapper.selectByPrimaryKey(roleId);
            //拿到角色对于的权限list
            List<Authority> authorityList = new ArrayList<>();
            RoleAuthExample roleAuthExample = new RoleAuthExample();//先找对应表的权限id集合
            RoleAuthExample.Criteria criteria1 = roleAuthExample.createCriteria();
            criteria1.andRoleIdEqualTo(roleId);
            List<cn.kebabshell.xiafan_demo.common.pojo.RoleAuth> roleAuths1 = roleAuthMapper.selectByExample(roleAuthExample);
            for (cn.kebabshell.xiafan_demo.common.pojo.RoleAuth roleAuth : roleAuths1) {
                //遍历对应的权限id集合，拿到权限list
                authorityList.add(authorityMapper.selectByPrimaryKey(roleAuth.getAuthId()));
            }
            //构成角色权限对象
            RoleAuth roleAuth = new RoleAuth(role, authorityList);
            //添加到这个user的角色权限
            roleAuths.add(roleAuth);
        }


        return new UserAuth(user, roleAuths);
    }

    @Override
    @Transactional
    public User register(User user) {
        Date date = new Date();
        user.setCreateTime(date);
        int insert = userMapper.insertSelective(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        //默认普通用户，角色id为2的是普通用户
        userRole.setRoleId(2L);
        userRole.setCreateTime(date);
        int insert2 = userRoleMapper.insertSelective(userRole);
        if (insert <= 0 || insert2 <= 0){
            throw new MyRegisterException("insert error");
        }
        return user;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        //根据id删除用户表
        userMapper.deleteByPrimaryKey(id);
        //根据id删除用户角色表
        //条件
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(id);
        userRoleMapper.deleteByExample(userRoleExample);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);

        //拿到id
        User user = userMapper.selectByExample(userExample).get(0);
        Long id = user.getId();

        //删除用户表
        userMapper.deleteByPrimaryKey(id);

        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria1 = userRoleExample.createCriteria();
        criteria1.andUserIdEqualTo(id);
        //删除用户角色表
        userRoleMapper.deleteByExample(userRoleExample);

        return true;
    }

    @Override
    public User updateByName(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(user.getName());
        userMapper.updateByExampleSelective(user, userExample);

        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public int getUserCount() {
        return userMapper.selectByExample(new UserExample()).size();
    }
}
