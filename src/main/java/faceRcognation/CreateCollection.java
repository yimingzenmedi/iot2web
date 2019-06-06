package faceRcognation;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import faceRcognation.ClientFactory;

public class CreateCollection {
    public void createCollection(String name){
        String collectionName = name;

        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionName);

        AmazonRekognition rekognition = ClientFactory.createClient();
        CreateCollectionResult result = rekognition.createCollection(request);

        Integer statusCode = result.getStatusCode();
        String collectionArn = result.getCollectionArn();
        String faceModelVersion = result.getFaceModelVersion();
        System.out.println("statusCode=" + statusCode + "\nARN="
                + collectionArn + "\nface model version=" + faceModelVersion);
    }
    }

