package cn.kebabshell.xiafan_demo.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by KebabShell
 * on 2019/11/29 下午 12:47
 * 用于保存图片
 */
@Component
public class FileSave {
    /**
     * 保存
     * @param file
     * @return
     */
    public String save(MultipartFile file) {
        String name = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + "_" + name;
        File dir = new File("/E:/workSpaceResource/xia_fan");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, newName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newName;
    }


}
