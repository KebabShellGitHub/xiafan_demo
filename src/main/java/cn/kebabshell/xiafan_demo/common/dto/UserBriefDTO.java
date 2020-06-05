package cn.kebabshell.xiafan_demo.common.dto;

import cn.kebabshell.xiafan_demo.common.custom.RoleAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBriefDTO {
    private Long id;
    private String userName;
    private String avatar;
    private String introduction;
    private Boolean effective;
    private RoleAuth roleAuth;
}
