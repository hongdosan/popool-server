package kr.co.popoolserver.infra.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.key.access}")
    private String ACCESS_KEY;

    @Value("${aws.key.secret}")
    private String SECRET_KEY;

    @Value("${aws.region.static}")
    private String REGION;

    /**
     * Amazon S3 Client Bean 등록
     * @return
     */
    @Bean
    public AmazonS3Client amazonS3Client(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(REGION)
                .build();
    }
}
