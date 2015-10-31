package com.mmontes.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.Calendar;

import static com.mmontes.util.Constants.AMAZON_S3_BUCKET_NAME;
import static com.mmontes.util.Constants.AMAZON_S3_ROOT_URL;

public class AmazonService {

    public static String uploadFile (String photoName,String fileContent) throws IOException {

        String[] parts = fileContent.split("data:")[1].split(";base64,");
        String contentType = parts[0];
        String content = parts[1];

        byte[] decodedContent = Base64.decodeBase64(content.getBytes());
        InputStream fis = new ByteArrayInputStream(decodedContent);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedContent.length);
        metadata.setContentType(contentType);
        metadata.setCacheControl("public, max-age=31536000");

        AmazonS3 s3 = new AmazonS3Client();
        String fileName = Calendar.getInstance().getTimeInMillis() + "_" + photoName;

        PutObjectRequest por = new PutObjectRequest(AMAZON_S3_BUCKET_NAME,fileName,fis, metadata);
        s3.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
        return AMAZON_S3_ROOT_URL + fileName;
    }
}
