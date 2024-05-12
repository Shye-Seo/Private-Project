package com.voucher.movie.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {

    @Value("#AccessKey")
    private String accessKey;

    @Value("#SecretKey")
    private String secretKey;

    @Value("ap-northeast-2")
    private String region;
    
//    public static final String newsFolder = "news-folder/"; //박물관 소식 파일
//    public static final String eventFolder = "event-folder/"; //이벤트 파일
//    public static final String noticeFolder = "notice-folder/"; //공고 파일
//    public static final String partnerFolder = "partner-folder/"; //제휴 파일
//    public static final String eduFolder = "edu-folder/"; //교육 파일
    
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
