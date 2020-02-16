package Service;

public class S3ConnectionService {

    public static AmazonS3 getClient(String accessKey, String secretKey){
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
    }

}
