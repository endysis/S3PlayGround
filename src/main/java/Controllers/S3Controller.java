package Controllers;

import Service.S3ConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class S3Controller {

    @GetMapping("/logStoredObjects")
    public Object logStoredObjects(@RequestParam(defaultValue = "accessKey") String accessKey, @RequestParam(defaultValue = "secretKey") String secretKey) {

        S3ConnectionService s3ConnectionService = new S3ConnectionService(accessKey, secretKey);

        ObjectListing objectListing = s3ConnectionService.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            LOG.info(os.getKey());
        }

        return ResponseEntity.ok("");
    }

    @GetMapping("/downloadImage")
    public Object downloadImage(@RequestParam(defaultValue = "accessKey") String accessKey,
                                @RequestParam(defaultValue = "secretKey") String secretKey,
                                @RequestParam(defaultValue = "bucketName") String bucketName,
                                @RequestParam(defaultValue = "objectKey") String objectKey) {

        S3Object s3object = S3ConnectionService.getClient(accessKey, secretKey)
                .getObject(bucketName, objectKey);

        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File("/downloadedPicture.jpg"));

        return ResponseEntity.ok("");
    }


    @GetMapping("/uploadImage")
    public Object uploadImage(@RequestParam(defaultValue = "accessKey") String accessKey,
                                @RequestParam(defaultValue = "secretKey") String secretKey,
                                @RequestParam(defaultValue = "bucketName") String bucketName,
                              @RequestParam(defaultValue = "objectKey") String newObjectKey) {

        S3Object s3object = S3ConnectionService.getClient(accessKey, secretKey).putObject(
                bucketName,
                newObjectKey,
                new File("/Users/douglaso/dev/S3_connect/src/main/java/PictureStore/booking.png")
        );

        return ResponseEntity.ok("");
    }
}
