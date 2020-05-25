package cn.kebabshell.xiafan_demo.server.service.user_service;

import cn.kebabshell.xiafan_demo.common.custom.UserAuth;
import cn.kebabshell.xiafan_demo.common.pojo.User;

import java.util.List;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:27
 */
public interface UserService {
    /**
     * 根据用户名找user
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 根据用户名找user，带有角色和权限信息
     * @param username
     * @return
     */
    UserAuth findByNameForAuth(String username);

    /**
     * 普通用户注册
     * @param user
     * @return
     */
    User register(User user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 根据name删除用户
     * @param name
     * @return
     */
    boolean deleteByName(String name);

    User updateByName(User user);

    /**
     * 用户记录条数
     * @return
     */
    int getUserCount();

    /**
     * 粉丝
     * @param userId
     * @return
     */
    List<User> getFollowMe(Long userId);

    /**
     * 我的关注
     * @param userId
     * @return
     */
    List<User> getMyFollow(Long userId);

    /**
     * 添加关注 userId 关注 followedUserId
     * @param userId 关注人
     * @param followedUserId 被关注人
     * @return
     */
    boolean addFollow(Long userId, Long followedUserId);

    /**
     * 取消关注
     * @param userId
     * @param followedUserId
     * @return
     */
    boolean delFollow(Long userId, Long followedUserId);
}
