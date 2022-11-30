package kr.co.popoolserver.common.infra.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

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

    /**
     * SecretKey, AccessKey 인증할 AwsCredentials Spring Bean 등록
     * @return
     */
    @Bean
    public AwsCredentials basicAWSCredentials(){
        return AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
    }

    /**
     * AWS Server와 통신할 S3 Client
     * @param awsCredentials
     * @return
     */
    @Bean
    public S3Client s3Client(AwsCredentials awsCredentials){
        return S3Client.builder()
                .region(Region.of(REGION))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    /**
     * 서명된 URL을 발급받기 위한 S3 Presigner
     * @param awsCredentials
     * @return
     */
    @Bean
    public S3Presigner s3Presigner(AwsCredentials awsCredentials){
        return S3Presigner.builder()
                .region(Region.of(REGION))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
