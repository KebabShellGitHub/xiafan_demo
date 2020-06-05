package cn.kebabshell.xiafan_demo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/6/3 下午 10:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicAddDTO {
    private Long id;
    private Long userId;
    private String name;
    private String info;
    private String path;
    private String userName;
    private Long sortId;
    private String sortName;
}
