package com.project.ds_helper.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUtil {

    private DataSize maxSize;
    private String[] allowedFileExtensions;

    public boolean isValidSizeAndExtension(MultipartFile file) throws IOException {
        // 용량 검사
        if(file.isEmpty() || maxSize.compareTo(DataSize.ofBytes(file.getSize())) < 0){
            throw new IOException("File Is Empty Or Size Invalid");
        }
        // 확장자 검사
        for(String extension : allowedFileExtensions){if(extension.equals(file.getContentType())){return true;}}
        return false;
    }

}
