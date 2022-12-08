package dao.dynamodb;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import dao.ImageDAO;

public class ImageS3DAO implements ImageDAO {

    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-west-1").build();
    private static final String bucketName = "tweeteruserphotos";

    @Override
    public String putImage(String userAlias, String imageBytes) {
        byte[] bytes = Base64.getDecoder().decode(imageBytes);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        PutObjectRequest req = new PutObjectRequest(bucketName, userAlias, stream, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(req);

        return s3.getUrl(bucketName, userAlias).toString();
    }


}
