package faceRcognation;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DescribeCollectionRequest;
import com.amazonaws.services.rekognition.model.DescribeCollectionResult;
import faceRcognation.ClientFactory;

public class DescribeCollection {
    public void describeCollection(String name){
        DescribeCollectionRequest request = new DescribeCollectionRequest()
                .withCollectionId(name);

        AmazonRekognition rekognition = ClientFactory.createClient();
        DescribeCollectionResult result = rekognition.describeCollection(request);

        System.out.println("ARN: " + result.getCollectionARN()
                + "\nFace Model Version: " + result.getFaceModelVersion()
                + "\nFace Count: " + result.getFaceCount()
                + "\nCreated: " + result.getCreationTimestamp());
    }

}
