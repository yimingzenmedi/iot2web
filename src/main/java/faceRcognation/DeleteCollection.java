package faceRcognation;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest;
import com.amazonaws.services.rekognition.model.DeleteCollectionResult;
import faceRcognation.ClientFactory;

public class DeleteCollection {
    public void deleteCollection(String name){
        String collectionId = name;

        DeleteCollectionRequest request = new DeleteCollectionRequest().withCollectionId(collectionId);
        AmazonRekognition rekognition = ClientFactory.createClient();
        DeleteCollectionResult result = rekognition.deleteCollection(request);

        Integer statusCode = result.getStatusCode();
        System.out.println("Status code: " + statusCode);
    }

}
