package com.mmontes.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File multipart2File(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }
}