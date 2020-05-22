package cn.kebabshell.xiafan_demo.common.dto;

import cn.kebabshell.xiafan_demo.common.pojo.PicComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KebabShell
 * on 2020/4/25 下午 02:34
 *
 * 图片评论DTO
 * 图片评论实体、图片评论点赞数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicCommentDTO {
    private PicComment picComment;
    private int likeCount;
}
