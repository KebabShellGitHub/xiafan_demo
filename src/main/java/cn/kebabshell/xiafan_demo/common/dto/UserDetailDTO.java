package cn.kebabshell.xiafan_demo.common.dto;

import cn.kebabshell.xiafan_demo.common.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/6/3 上午 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
    private User user;
    private Long fans;
    private Long follows;

}
