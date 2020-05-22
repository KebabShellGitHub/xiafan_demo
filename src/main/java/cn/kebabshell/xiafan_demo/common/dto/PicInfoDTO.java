package cn.kebabshell.xiafan_demo.common.dto;

import cn.kebabshell.xiafan_demo.common.pojo.Pic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:30
 *
 * 图片详情DTO
 * 图片实体、发布人(用户名)、分类id、分类名、点赞数、你是否关注了、访问次数、评论数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicInfoDTO {
    private Pic pic;
    private String userName;
    private Long sortId;
    private String sortName;
    private long likeCount;
    private boolean isFollow;
    private long hitCount;
    private long commentCount;
}
