package cn.kebabshell.xiafan_demo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/5/22 下午 04:12
 *
 * 图片简介DTO
 * 图片id、发布人(id)、发布人(用户名)、你是否关注了、图片名称、图片路径、访问次数、分类id、分类名、评论数、点赞数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicBriefDTO {
    private Long picId;
    private Long userId;
    private String userName;
    private boolean isFollow;
    private String picName;
    private String path;
    private long hitCount;
    private Long sortId;
    private String sortName;
    private long commentCount;
    private long likeCount;

}
