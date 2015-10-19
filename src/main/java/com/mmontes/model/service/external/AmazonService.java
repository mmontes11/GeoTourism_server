package com.mmontes.model.service.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.Calendar;

import static com.mmontes.util.Constants.AMAZON_S3_BUCKET_NAME;
import static com.mmontes.util.Constants.AMAZON_S3_ROOT_URL;

public class AmazonService {

    public static String uploadFile (String name,String photoName,String fileContent) throws IOException {
        AmazonS3 s3 = new AmazonS3Client();
        String fileName = Calendar.getInstance().getTimeInMillis() + "_" + photoName;

        byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((fileContent.substring(fileContent.indexOf(",")+1)).getBytes());
        InputStream fis = new ByteArrayInputStream(bI);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bI.length);
        metadata.setContentType("image/png");
        metadata.setCacheControl("public, max-age=31536000");

        PutObjectRequest por = new PutObjectRequest(AMAZON_S3_BUCKET_NAME,fileName,fis, metadata);
        s3.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
        return AMAZON_S3_ROOT_URL + fileName;
    }
}
