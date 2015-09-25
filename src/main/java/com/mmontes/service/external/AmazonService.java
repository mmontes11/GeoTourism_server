package com.mmontes.service.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.Calendar;

import static com.mmontes.util.GlobalNames.AMAZON_S3_BUCKET_NAME;
import static com.mmontes.util.GlobalNames.AMAZON_S3_ROOT_URL;

public class AmazonService {

    private static File createFile(String fileName,String extension,String fileContent) throws IOException {
        File file = File.createTempFile(fileName, extension);
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(fileContent);
        writer.close();

        return file;
    }

    public static String uploadFile (String name,String extension,String fileContent) throws Exception {
        AmazonS3 s3 = new AmazonS3Client();
        String fileName = name + "_" + Calendar.getInstance().getTimeInMillis() + extension;
        PutObjectRequest por = new PutObjectRequest(AMAZON_S3_BUCKET_NAME,fileName,createFile(fileName,extension,fileContent));
        s3.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
        return AMAZON_S3_ROOT_URL + fileName;
    }
}
