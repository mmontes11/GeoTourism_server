package com.mmontes.rest.controller;

import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.service.AmazonService;
import com.mmontes.util.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class PhotoController {

    @RequestMapping(value = "/admin/photo")
    public ResponseEntity
    upload(@RequestParam("file") MultipartFile multiPartFile) {
        String url;
        if (!multiPartFile.isEmpty()) {
            try {
                File file = FileUtils.multipart2File(multiPartFile);
                url = AmazonService.uploadS3(file);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseFactory.getCustomJSON("url",url), HttpStatus.OK);
    }
}
