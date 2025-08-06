package com.project.ds_helper.domain.post.util;

import com.project.ds_helper.domain.post.entity.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    public static String toStoredFilename(MultipartFile file){
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis())
                .append(file.getOriginalFilename());
        return sb.toString();
    }

}
