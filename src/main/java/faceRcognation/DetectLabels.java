package faceRcognation;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import faceRcognation.ClientFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DetectLabels {

    public void detecLable(String path) {
        String imgPath = path;
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(imgPath));
        } catch (IOException e) {
            System.out.println(System.getProperty("user.dir"));
            System.err.println("Failed to load image: " + e.getMessage());
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        AmazonRekognition rekognition = ClientFactory.createClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withBytes(byteBuffer))
                .withMaxLabels(10);
        DetectLabelsResult result = rekognition.detectLabels(request);

        List<Label> labels = result.getLabels();
        for (Label label : labels) {
            System.out.println(label.getName() + ": " + label.getConfidence());
        }
    }

}