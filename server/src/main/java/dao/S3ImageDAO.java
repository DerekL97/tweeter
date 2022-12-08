package dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.util.Base64;


public class S3ImageDAO implements ImageDAO {
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    private static final String bucketName = "bentweeterpics";



    @Override
    public String uploadImage(String userAlias, String imageBytes) {
        byte[] bytes = Base64.getDecoder().decode(imageBytes);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        PutObjectRequest req = new PutObjectRequest(bucketName, userAlias, stream, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(req);

        return s3.getUrl(bucketName, userAlias).toString();
    }

}
